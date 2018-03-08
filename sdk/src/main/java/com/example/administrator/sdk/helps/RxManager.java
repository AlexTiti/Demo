package com.example.administrator.sdk.helps;


import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;



/**
 * @author Administrator
 * 用于管理Rxjava 注册订阅和取消订阅
 */
public class RxManager {
    private final CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    public void register(Disposable d) {
        mCompositeDisposable.add(d);
    }

    public void unSubscribe() {
        mCompositeDisposable.dispose();// 取消订阅
    }
}