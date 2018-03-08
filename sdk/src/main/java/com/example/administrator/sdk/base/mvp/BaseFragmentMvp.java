package com.example.administrator.sdk.base.mvp;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.transition.TransitionInflater;
import android.view.View;

import com.example.administrator.sdk.R;
import com.example.administrator.sdk.base.BaseCompatActivity;
import com.example.administrator.sdk.base.fragment.BaseCompatFragment;
import com.example.administrator.sdk.base.fragment.IBaseFragment;

import me.yokeyword.fragmentation.SupportFragment;


/**
 * <pre>
 *
 *   @author   :   Alex
 *   @e_mail   :   18238818283@sina.cn
 *   @time     :   2018/01/12
 *   @desc     :
 *   @version  :   V 1.0.9
 */

public abstract class BaseFragmentMvp<V extends Contract.ViewMvp,P extends BasePresenterMvp>
        extends BaseCompatFragment implements IBaseFragment{

    private P present;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        present = createPresent();
        present.onAttach((V)this);
    }

    public P getPresent() {
        return present;
    }

    /**
     *  创建Present
     * @return
     */
    protected abstract P createPresent();

    @Override
    public void onDestroy() {
        super.onDestroy();
        present.disAttach();
    }

    public void startActivityBeforeLOLLIPOP(Activity activity , Class c){
        startActivity(new Intent(activity,c));
    }
    /**
     * start Activity
     * @param activity
     * @param c
     */
    public void startActivityUseLOLLIPOP(Activity activity, Class c){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(activity);
            startActivity(new Intent(activity,c),options.toBundle());
        }else {
            startActivityBeforeLOLLIPOP(activity,c);
        }
    }

    /**
     * start CompatActivity
     * @param activity
     * @param c
     */
    public void startAppCompatActivityUseLOLLIPOP(Activity activity, Class c){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().setExitTransition(TransitionInflater.from(activity).inflateTransition(R.transition.trans));
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(activity);
            startActivity(new Intent(activity,c),options.toBundle());
        }else {
            startActivityBeforeLOLLIPOP(activity,c);
        }
    }

    /**
     * Share View
     * @param activity
     * @param c
     * @param view
     * @param transName
     */
    public void startActivityUseShare(Activity activity, Class c, View view, String transName){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(activity,view,transName);
            startActivity(new Intent(activity,c),options.toBundle());
        }else {
            startActivityBeforeLOLLIPOP(activity,c);
        }
    }

    public void startCompatActivityUseShare(Activity activity,  View view, String transName,Intent intent){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().setSharedElementExitTransition(TransitionInflater.from(activity).inflateTransition(R.transition.change));
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(activity,view,transName);
            startActivity(intent,options.toBundle());
        }else {
            startActivity(intent);
        }
    }

    @Override
    public void startNewFragment(@NonNull SupportFragment supportFragment) {
        start(supportFragment);
    }

    @Override
    public void startNewFragmentWithPop(@NonNull SupportFragment supportFragment) {
        startWithPop(supportFragment);
    }

    @Override
    public void startNewFragmentForResult(@NonNull SupportFragment supportFragment, int
            requestCode) {
        startForResult(supportFragment, requestCode);
    }

    @Override
    public void popToFragment(Class<?> targetFragmentClass, boolean includeTargetFragment) {
        popTo(targetFragmentClass, includeTargetFragment);
    }

    @Override
    public void setOnFragmentResult(int resultCode, Bundle data) {
        setFragmentResult(resultCode, data);
    }

    @Override
    public void startNewActivity(@NonNull Class<?> clz) {
        ((BaseCompatActivity) mActivity).startActivity(clz);
    }

    @Override
    public void startNewActivity(@NonNull Class<?> clz, Bundle bundle) {
        ((BaseCompatActivity) mActivity).startActivity(clz, bundle);
    }

    @Override
    public void startNewActivityForResult(@NonNull Class<?> clz, Bundle bundle, int requestCode) {
        ((BaseCompatActivity) mActivity).startActivityForResult(clz, bundle, requestCode);
    }

    @Override
    public boolean isVisiable() {
        return isSupportVisible();
    }

    @Override
    public Activity getBindActivity() {
        return mActivity;
    }
}
