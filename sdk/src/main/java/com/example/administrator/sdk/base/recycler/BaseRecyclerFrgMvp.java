package com.example.administrator.sdk.base.recycler;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.sdk.R;

import com.example.administrator.sdk.base.mvp.BaseFragmentMvp;
import com.example.administrator.sdk.base.mvp.BasePresenterMvp;
import com.example.administrator.sdk.base.mvp.Contract;

/**
 * <pre>
 *
 *   @author   :   Alex
 *   @e_mail   :   18238818283@sina.cn
 *   @time     :   2018/01/17
 *   @desc     :
 *   @version  :   V 1.0.9
 */

public abstract class BaseRecyclerFrgMvp<V extends Contract.ViewMvp,P extends BasePresenterMvp> extends BaseFragmentMvp<V,P> {

    /**
     * 网络异常View
     */
    protected View errorView;
    /**
     * loadingView
     */
    protected View loadingView;
    /**
     * 没有内容view
     */
    protected View emptyView;

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        showLoading();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        errorView = inflater.inflate(R.layout.view_network_error, container, false);
        loadingView = inflater.inflate(R.layout.view_loading, container, false);
        emptyView = inflater.inflate(R.layout.view_empty, container, false);
        errorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoading();
                onErrorViewClick(v);
            }
        });
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    /**
     * 网络异常view被点击时触发，由子类实现
     *
     * @param view view
     */
    protected abstract void onErrorViewClick(View view);

    /**
     * 显示加载中view，由子类实现
     */
    protected abstract void showLoading();
}
