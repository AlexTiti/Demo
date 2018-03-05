package com.example.administrator.sdk.base.recycler_base;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;


import java.util.List;

/**
 * <pre>
 *
 *   @author   :   Alex
 *   @e_mail   :   18238818283@sina.cn
 *   @time     :   2018/01/18
 *   @desc     :
 *   @version  :   V 1.0.9
 */

public abstract class BaseRecyclerViewAdapter<T, K extends BaseViewHolder> extends BaseQuickAdapter<T,K>{

    public BaseRecyclerViewAdapter(int layoutResId, @Nullable List<T> data) {
        super(layoutResId, data);
        init();
    }

    public BaseRecyclerViewAdapter(@Nullable List<T> data) {
        super(data);
        init();
    }

    public BaseRecyclerViewAdapter(int layoutResId) {
        super(layoutResId);
        init();
    }

    private void init(){
        setLoadMoreView(new RecyclerLoadView());
        setEnableLoadMore(true);
        isFirstOnly(false);
        //openLoadAnimation(new ScaleInAnimation(0.8f));
    }


}
