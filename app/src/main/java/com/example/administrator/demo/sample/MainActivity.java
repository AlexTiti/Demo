package com.example.administrator.demo.sample;


import android.os.Bundle;
import android.view.View;

import com.example.administrator.demo.R;
import com.example.administrator.sdk.base.mvp.BaseActivityMvp;
import com.example.administrator.sdk.base.mvp.BasePresenterMvp;

/**
 * @author Administrator
 */
public class MainActivity extends BaseActivityMvp {



    @Override
    public void initDataFromServer() {

    }

    @Override
    protected BasePresenterMvp createPresent() {
        return null;
    }

    @Override
    protected void showLoading() {

    }

    @Override
    protected void onErrorViewClick(View v) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        loadRootFragment(R.id.container,MovieListFragment.newInstance());

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main4;
    }
}
