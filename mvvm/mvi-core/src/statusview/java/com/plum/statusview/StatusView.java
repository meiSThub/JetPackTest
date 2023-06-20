package com.plum.statusview;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.plum.mvi.core.R;

import java.util.ArrayList;

/**
 * 类描述：多种状态切换的view（无网、空数据、http错误、正常）.
 */
@SuppressWarnings("unused")
public class StatusView extends RelativeLayout {
    protected static final LayoutParams DEFAULT_LAYOUT_PARAMS =
            new LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.MATCH_PARENT);

    public static final int STATUS_CONTENT = 0x00;
    public static final int STATUS_LOADING = 0x01;
    public static final int STATUS_EMPTY = 0x02;
    public static final int STATUS_ERROR = 0x03;
    public static final int STATUS_NO_NETWORK = 0x04;

    private static final int NULL_RESOURCE_ID = -1;

    private View mEmptyView;
    private View mErrorView;
    private View mLoadingView;
    private View mNoNetworkView;
    private View mContentView;

    private int mEmptyViewResId;
    private int mErrorViewResId;
    private int mLoadingViewResId;
    private int mNoNetworkViewResId;
    private int mContentViewResId;

    private int mViewStatus = -1;
    protected final LayoutInflater mInflater;
    private OnClickListener mOnRetryClickListener;
    private OnViewStatusChangeListener mViewStatusListener;

    protected final ArrayList<Integer> mOtherIds = new ArrayList<>();

    public StatusView(Context context) {
        this(context, null);
    }

    public StatusView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StatusView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mInflater = LayoutInflater.from(getContext());
        initStyleAttr(context, attrs, defStyleAttr);
    }

    protected void initStyleAttr(Context context, AttributeSet attrs, int defStyleAttr) {
        final TypedArray a = context.obtainStyledAttributes(
                attrs, R.styleable.StatusView, defStyleAttr, 0);
        mEmptyViewResId = a.getResourceId(
                R.styleable.StatusView_emptyView, R.layout.empty_view);
        mErrorViewResId = a.getResourceId(
                R.styleable.StatusView_errorView, R.layout.error_view);
        mLoadingViewResId = a.getResourceId(
                R.styleable.StatusView_loadingView, R.layout.loading_view);
        mNoNetworkViewResId = a.getResourceId(
                R.styleable.StatusView_noNetworkView, R.layout.no_network_view);
        mContentViewResId = a.getResourceId(
                R.styleable.StatusView_contentView, NULL_RESOURCE_ID);
        a.recycle();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        showContent();
    }

    /**
     * 获取当前状态.
     *
     * @return 视图状态
     */
    public int getViewStatus() {
        return mViewStatus;
    }

    /**
     * 设置重试点击事件.
     *
     * @param onRetryClickListener 重试点击事件
     */
    public void setOnRetryClickListener(OnClickListener onRetryClickListener) {
        this.mOnRetryClickListener = onRetryClickListener;
    }

    /**
     * 显示空视图.
     */
    public final void showEmpty() {
        showEmpty(mEmptyViewResId, DEFAULT_LAYOUT_PARAMS);
    }

    /**
     * 显示空视图.
     *
     * @param hint 自定义提示文本内容
     */
    public final void showEmpty(String hint) {
        showEmpty();
        setStatusHintContent(mEmptyView, hint);
    }

    /**
     * 展示空视图
     *
     * @param drawableId 图片id
     * @param text       文字内容
     * @param textColor  文字颜色
     */
    public final void showEmpty(int drawableId, String text, int textColor) {
        showEmpty();
        if (mEmptyView != null) {
            ImageView iv = mEmptyView.findViewById(R.id.iv_empty_view);
            if (iv != null) {
                iv.setImageResource(drawableId);
            }
            TextView hintView = mEmptyView.findViewById(R.id.status_hint_content);
            if (hintView != null) {
                hintView.setText(text);
                if (textColor != 0) {
                    hintView.setTextColor(textColor);
                }
            }
        }
    }

    /**
     * 展示空视图
     *
     * @param drawableId 图片id
     * @param textId     文字id
     * @param textColor  文字颜色
     */
    public final void showEmpty(int drawableId, int textId, int textColor) {
        showEmpty(drawableId, getContext().getString(textId), textColor);
    }

    /**
     * 显示空视图.
     *
     * @param layoutId     自定义布局文件
     * @param layoutParams 布局参数
     */
    public final void showEmpty(int layoutId, ViewGroup.LayoutParams layoutParams) {
        showEmpty(null == mEmptyView ? inflateView(layoutId) : mEmptyView, layoutParams);
    }

    /**
     * 显示空视图.
     *
     * @param view         自定义视图
     * @param layoutParams 布局参数
     */
    private void showEmpty(View view, ViewGroup.LayoutParams layoutParams) {
        checkNull(view, "Empty view is null.");
        checkNull(layoutParams, "Layout params is null.");
        changeViewStatus(STATUS_EMPTY);
        if (null == mEmptyView) {
            mEmptyView = view;
            View emptyRetryView = mEmptyView.findViewById(R.id.empty_retry_view);
            if (null != mOnRetryClickListener && null != emptyRetryView) {
                emptyRetryView.setOnClickListener(mOnRetryClickListener);
            }
            mOtherIds.add(mEmptyView.getId());
            addView(mEmptyView, 0, layoutParams);
        }
        showViewById(mEmptyView.getId());
    }

    /**
     * 显示错误视图.
     */
    public final void showError() {
        showError(mErrorViewResId, DEFAULT_LAYOUT_PARAMS);
    }

    /**
     * 显示错误视图.
     *
     * @param hint 自定义提示文本内容
     */
    public final void showError(String hint) {
        showError();
        setStatusHintContent(mErrorView, hint);
    }

    /**
     * 显示错误视图.
     *
     * @param hint      自定义提示文本内容
     * @param retryHint 自定义重试文本内容
     */
    public final void showError(String hint, String retryHint) {
        showError();
        setStatusHintContent(mErrorView, hint);
        setStatusRetryHintContent(mErrorView, retryHint);
    }

    /**
     * 显示错误视图.
     *
     * @param hint      自定义提示文本内容
     * @param retryHint 自定义重试文本内容
     * @param imgRes    自定义状态图标内容
     */
    public final void showError(String hint, String retryHint, int imgRes) {
        showError();
        setStatusHintContent(mErrorView, hint);
        setStatusRetryHintContent(mErrorView, retryHint);
        setStatusImgRes(mErrorView, imgRes);
    }

    /**
     * 显示错误视图.
     *
     * @param layoutId     自定义布局文件
     * @param layoutParams 布局参数
     */
    public final void showError(int layoutId, ViewGroup.LayoutParams layoutParams) {
        showError(null == mErrorView ? inflateView(layoutId) : mErrorView, layoutParams);
    }

    /**
     * 显示错误视图.
     *
     * @param view         自定义视图
     * @param layoutParams 布局参数
     */
    private void showError(View view, ViewGroup.LayoutParams layoutParams) {
        checkNull(view, "Error view is null.");
        checkNull(layoutParams, "Layout params is null.");
        changeViewStatus(STATUS_ERROR);
        if (null == mErrorView) {
            mErrorView = view;
            View errorRetryView = mErrorView.findViewById(R.id.error_retry_view);
            if (null != mOnRetryClickListener && null != errorRetryView) {
                initRetryView(errorRetryView);
                errorRetryView.setOnClickListener(mOnRetryClickListener);
            }
            mOtherIds.add(mErrorView.getId());
            addView(mErrorView, 0, layoutParams);
        }
        showViewById(mErrorView.getId());
    }

    /**
     * 初始化重试按钮.
     */
    public void initRetryView(@NonNull View view) {
    }

    /**
     * 显示加载中视图.
     */
    public final void showLoading() {
        showLoading(mLoadingViewResId, DEFAULT_LAYOUT_PARAMS);
    }

    /**
     * 显示加载中视图.
     *
     * @param hint 自定义提示文本内容
     */
    public final void showLoading(String hint) {
        showLoading();
        setStatusHintContent(mLoadingView, hint);
    }

    /**
     * 显示加载中视图.
     *
     * @param layoutId     自定义布局文件
     * @param layoutParams 布局参数
     */
    public final void showLoading(int layoutId, ViewGroup.LayoutParams layoutParams) {
        showLoading(null == mLoadingView ? inflateView(layoutId) : mLoadingView, layoutParams);
    }

    /**
     * 显示加载中视图.
     *
     * @param view         自定义视图
     * @param layoutParams 布局参数
     */
    private void showLoading(View view, ViewGroup.LayoutParams layoutParams) {
        checkNull(view, "Loading view is null.");
        checkNull(layoutParams, "Layout params is null.");
        changeViewStatus(STATUS_LOADING);
        if (null == mLoadingView) {
            mLoadingView = view;
            mOtherIds.add(mLoadingView.getId());
            addView(mLoadingView, 0, layoutParams);
        }
        showViewById(mLoadingView.getId());
    }

    /**
     * 显示无网络视图.
     */
    public final void showNoNetwork() {
        showNoNetwork(mNoNetworkViewResId, DEFAULT_LAYOUT_PARAMS);
    }

    /**
     * 显示无网络视图.
     *
     * @param hint 自定义提示文本内容
     */
    public final void showNoNetwork(String hint) {
        showNoNetwork();
        setStatusHintContent(mNoNetworkView, hint);
    }

    /**
     * 显示无网络视图.
     *
     * @param layoutId     自定义布局文件
     * @param layoutParams 布局参数
     */
    public final void showNoNetwork(int layoutId, ViewGroup.LayoutParams layoutParams) {
        showNoNetwork(null == mNoNetworkView
                ? inflateView(layoutId) : mNoNetworkView, layoutParams);
    }

    /**
     * 显示无网络视图.
     *
     * @param view         自定义视图
     * @param layoutParams 布局参数
     */
    private void showNoNetwork(View view, ViewGroup.LayoutParams layoutParams) {
        checkNull(view, "No network view is null.");
        checkNull(layoutParams, "Layout params is null.");
        changeViewStatus(STATUS_NO_NETWORK);
        if (null == mNoNetworkView) {
            mNoNetworkView = view;
            View noNetworkRetryView = mNoNetworkView.findViewById(R.id.no_network_retry_view);
            if (null != mOnRetryClickListener && null != noNetworkRetryView) {
                noNetworkRetryView.setOnClickListener(mOnRetryClickListener);
            }
            mOtherIds.add(mNoNetworkView.getId());
            addView(mNoNetworkView, 0, layoutParams);
        }
        showViewById(mNoNetworkView.getId());
    }

    /**
     * 显示内容视图.
     */
    public final void showContent() {
        changeViewStatus(STATUS_CONTENT);
        if (null == mContentView && mContentViewResId != NULL_RESOURCE_ID) {
            mContentView = mInflater.inflate(mContentViewResId, null);
            addView(mContentView, 0, DEFAULT_LAYOUT_PARAMS);
        }
        showContentView();
    }

    /**
     * 显示内容视图.
     *
     * @param layoutId     自定义布局文件
     * @param layoutParams 布局参数
     */
    public final void showContent(int layoutId, ViewGroup.LayoutParams layoutParams) {
        showContent(inflateView(layoutId), layoutParams);
    }

    /**
     * 显示内容视图.
     *
     * @param view         自定义视图
     * @param layoutParams 布局参数
     */
    private void showContent(View view, ViewGroup.LayoutParams layoutParams) {
        checkNull(view, "Content view is null.");
        checkNull(layoutParams, "Layout params is null.");
        changeViewStatus(STATUS_CONTENT);
        clear(mContentView);
        mContentView = view;
        addView(mContentView, 0, layoutParams);
        showViewById(mContentView.getId());
    }

    protected void setStatusHintContent(View view, String hint) {
        checkNull(view, "Target view is null.");
        TextView hintView = view.findViewById(R.id.status_hint_content);
        if (null != hintView) {
            hintView.setText(hint);
        } else {
            throw new NullPointerException("Not find the view ID `status_hint_content`");
        }
    }

    /**
     * 设置重试按钮内容
     */
    protected void setStatusRetryHintContent(View view, String retryHint) {
        checkNull(view, "Target view is null.");
        TextView retryHintView = view.findViewById(R.id.error_retry_view);
        if (null != retryHintView) {
            retryHintView.setText(retryHint);
        } else {
            throw new NullPointerException("Not find the view ID `error_retry_view`");
        }
    }

    /**
     * 设置状态图标内容
     */
    protected void setStatusImgRes(View view, int imgRes) {
        checkNull(view, "Target view is null.");
        ImageView statusHintIcon = view.findViewById(R.id.status_hint_icon);
        if (null != statusHintIcon) {
            statusHintIcon.setImageResource(imgRes);
        } else {
            throw new NullPointerException("Not find the view ID `status_hint_icon`");
        }
    }

    protected View inflateView(int layoutId) {
        return mInflater.inflate(layoutId, null);
    }

    protected void showViewById(int viewId) {
        final int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = getChildAt(i);
            view.setVisibility(view.getId() == viewId ? View.VISIBLE : View.GONE);
        }
    }

    private void showContentView() {
        final int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = getChildAt(i);
            view.setVisibility(mOtherIds.contains(view.getId()) ? View.GONE : View.VISIBLE);
        }
    }

    protected void checkNull(Object object, String hint) {
        if (null == object) {
            throw new NullPointerException(hint);
        }
    }

    private void clear(View... views) {
        if (null == views) {
            return;
        }
        for (View view : views) {
            if (null != view) {
                removeView(view);
            }
        }
    }

    /**
     * 视图状态改变接口.
     */
    public interface OnViewStatusChangeListener {
        /**
         * 视图状态改变时回调.
         *
         * @param oldViewStatus 之前的视图状态
         * @param newViewStatus 新的视图状态
         */
        void onChange(int oldViewStatus, int newViewStatus);
    }

    /**
     * 设置视图状态改变监听事件.
     *
     * @param onViewStatusChangeListener 视图状态改变监听事件
     */
    public void setOnViewStatusChangeListener(
            OnViewStatusChangeListener onViewStatusChangeListener) {
        this.mViewStatusListener = onViewStatusChangeListener;
    }

    /**
     * 改变视图状态.
     *
     * @param newViewStatus 新的视图状态
     */
    protected void changeViewStatus(int newViewStatus) {
        if (mViewStatus == newViewStatus) {
            return;
        }
        if (null != mViewStatusListener) {
            mViewStatusListener.onChange(mViewStatus, newViewStatus);
        }
        mViewStatus = newViewStatus;
    }

    private void setContentViewResId(int contentViewResId) {
        this.mContentViewResId = contentViewResId;
        this.mContentView = mInflater.inflate(mContentViewResId, null);
        addView(mContentView, 0, DEFAULT_LAYOUT_PARAMS);
    }

    public void setContentView(View contentView) {
        this.mContentView = contentView;
        addView(mContentView, 0, DEFAULT_LAYOUT_PARAMS);
    }

    public static StatusView attach(Fragment fragment, int rootAnchor) {
        if (null == fragment || fragment.getView() == null) {
            throw new IllegalArgumentException("fragment is null or fragment.getView is null");
        }
        if (-1 != rootAnchor) {
            ViewGroup contentAnchor = fragment.getView().findViewById(rootAnchor);
            if (null != contentAnchor) {
                attach(contentAnchor);
            }
        }
        ViewGroup contentParent = (ViewGroup) fragment.getView().getParent();
        return attach(contentParent);
    }

    public static StatusView attach(Activity activity, int rootAnchor) {
        if (-1 != rootAnchor) {
            ViewGroup contentAnchor = activity.findViewById(rootAnchor);
            if (null != contentAnchor) {
                attach(contentAnchor);
            }
        }
        ViewGroup defaultAnchor = activity.findViewById(android.R.id.content);
        return attach(defaultAnchor);
    }

    public static StatusView attach(ViewGroup rootAnchor) {
        if (null == rootAnchor) {
            throw new IllegalArgumentException("root Anchor View can't be null");
        }
        ViewGroup parent = (ViewGroup) rootAnchor.getParent();
        int anchorIndex = parent.indexOfChild(rootAnchor);
        if (-1 != anchorIndex) {
            parent.removeView(rootAnchor);
            StatusView statusView = new StatusView(rootAnchor.getContext());
            statusView.setContentView(rootAnchor);
            ViewGroup.LayoutParams p = rootAnchor.getLayoutParams();
            parent.addView(statusView, anchorIndex, p);
            return statusView;
        }
        return null;
    }
}