package com.example.administrator.sdk.base.mvp;

import com.example.administrator.sdk.helps.RxManager;

import io.reactivex.functions.Consumer;

/**
 * <pre>
 *
 *   @author   :   Alex
 *   @e_mail   :   18238818283@sina.cn
 *   @time     :   2017/12/12
 *   @desc     :
 *   @version  :   V 1.0.9
 */

public abstract class BasePresenterMvp<T,V extends Contract.ViewMvp,M extends Contract.ModeMvp> implements Consumer<T> {

    private V mView;
    protected RxManager mRxManager = new RxManager();
    public void onAttach(V mView){
        this.mView = mView;
    }

    public V getView() {
        return mView;
    }

    public void disAttach(){
        mRxManager.unSubscribe();
        if (mView != null){
            mView = null;
        }
    }


   public M getMode(){
       return createModel();

   }

    public abstract M createModel();

//    @Override
//    public void onSubscribe(Disposable d) {
//
//    }
//
//    @Override
//    public void onNext(T t) {
//        if (getmView() != null){
//            getmView().loadSuccess(t);
//        }
//
//    }
//
//    @Override
//    public void onError(Throwable e) {
//        if (getmView() != null){
//            getmView().loadFailed(e.toString());
//        }
//    }
//
//    @Override
//    public void onComplete() {
//
//    }


    @Override
    public void accept(T t) throws Exception {
        if (getView() != null){
            getView().loadSuccess(t);
        }
    }
}
