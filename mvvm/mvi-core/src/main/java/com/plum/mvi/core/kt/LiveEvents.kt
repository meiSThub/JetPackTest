package com.plum.mvi.core.kt

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

/**
 * @Author: meixianbing
 * @Date: 2023/6/19 上午11:46
 * @Description: 负责处理多维度一次性Event，支持多个监听者
 * 比如：我们在请求开始时发出ShowLoading，网络请求成功后发出DismissLoading与Toast事件
 * 如果我们在请求开始后回到桌面，成功后再回到App,这样有一个事件就会被覆盖，因此将所有事件通过List存储
 */
class LiveEvents<T> : MutableLiveData<List<T>>() {
    private val observers = hashSetOf<ObserverWrapper<in T>>()

    override fun observe(owner: LifecycleOwner, observer: Observer<in List<T>>) {
        observers.find { it.observer === observer }?.let {
            return // existing
        }
        val wrapper = ObserverWrapper(observer)
        observers.add(wrapper)
        super.observe(owner, wrapper)
    }

    override fun observeForever(observer: Observer<in List<T>>) {
        observers.find { it.observer === observer }?.let {
            return // 已经存在
        }
        val wrapper = ObserverWrapper(observer)
        observers.add(wrapper)
        super.observeForever(wrapper)
    }

    override fun removeObserver(observer: Observer<in List<T>>) {
        if (observer is ObserverWrapper<*> && observers.remove(observer)) {
            super.removeObserver(observer)
            return
        }
        val iterator = observers.iterator()
        while (iterator.hasNext()) {
            val wrapper = iterator.next()
            if (wrapper.observer == observer) {
                iterator.remove()
                super.removeObserver(wrapper)
                break
            }
        }
    }

    override fun setValue(value: List<T>?) {
        observers.forEach { it.setValue(value) }
        super.setValue(value)
    }

    private class ObserverWrapper<T>(val observer: Observer<in List<T>>) : Observer<List<T>> {
        private val pending = AtomicBoolean(false)
        private val eventList = mutableListOf<List<T>>()

        override fun onChanged(t: List<T>) {
            if (pending.compareAndSet(true, false)) {
                observer.onChanged(eventList.flatten())
                eventList.clear()
            }
        }

        fun setValue(list: List<T>?) {
            pending.set(true)
            list?.let { eventList.add(it) }
        }
    }
}