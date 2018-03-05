package com.example.administrator.sdk.base.mvp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.administrator.sdk.base.BaseCompatActivity;

/**
 * @author Administrator
 */
public abstract class BaseActivityMvp<V extends Contract.ViewMvp,P extends BasePresenterMvp> extends BaseCompatActivity {

    private P present;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        present = createPresent();
        if (present != null) {
            present.onAttach((V) this);
        }
        initDataFromServer();
    }

    /**
     * 获取数据
     */
    public abstract void initDataFromServer();

    /**
     * 获取Present
     * @return P
     */
    public P getPresent() {
        return present;
    }

    /**
     * 创建Present
     * @return P
     */
    protected abstract P createPresent();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (present != null) {
            present.disAttach();
        }
    }
}
