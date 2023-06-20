package com.plum.mvi.core.kt

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.map
import kotlin.reflect.KProperty1

/**
 * @Author: meixianbing
 * @Date: 2023/6/19 下午1:36
 * @Description: LiveData扩展方法
 */

/**
 * 监听一个属性
 */
fun <T, A> LiveData<T>.observeState(
    lifecycleOwner: LifecycleOwner,
    prop1: KProperty1<T, A>,
    action: (A) -> Unit
) {
    Log.i("TAG", "observeState: value=$value")
    this.map { prop1.get(it) }.distinctUntilChanged().observe(lifecycleOwner) { a ->
        action.invoke(a)
    }
}

/**
 * 监听两个属性
 */
fun <T, A, B> LiveData<T>.observeState(
    lifecycleOwner: LifecycleOwner,
    prop1: KProperty1<T, A>,
    prop2: KProperty1<T, B>,
    action: (A, B) -> Unit
) {
    this.map {
        Pair(prop1.get(it), prop2.get(it))
    }.distinctUntilChanged().observe(lifecycleOwner) { (a, b) ->
        action.invoke(a, b)
    }
}

/**
 * 监听三个属性
 */
fun <T, A, B, C> LiveData<T>.observeState(
    lifecycleOwner: LifecycleOwner,
    prop1: KProperty1<T, A>,
    prop2: KProperty1<T, B>,
    prop3: KProperty1<T, C>,
    action: (A, B, C) -> Unit
) {
    this.map {
        Triple(prop1.get(it), prop2.get(it), prop3.get(it))
    }.distinctUntilChanged().observe(lifecycleOwner) { (a, b, c) ->
        action.invoke(a, b, c)
    }
}

/**
 * 设置状态
 */
fun <T> MutableLiveData<T>.setState(reducer: T.() -> T) {
    this.value = this.value?.reducer()
}

/**
 * 设置事件
 */
fun <T> LiveEvents<T>.setEvent(vararg values: T) {
    this.value = values.toList()
}

fun <T> MutableLiveData<T>.asLiveData(): LiveData<T> {
    return this
}

/**
 * 监听事件
 */
fun <T> LiveData<List<T>>.observeEvent(lifecycleOwner: LifecycleOwner, action: (T) -> Unit) {
    this.observe(lifecycleOwner) { eventList ->
        eventList.forEach { event ->
            action.invoke(event)
        }
    }
}
