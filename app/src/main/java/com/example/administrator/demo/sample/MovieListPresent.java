package com.example.administrator.demo.sample;

import com.example.administrator.sdk.base.mvp.BasePresenterMvp;

import io.reactivex.functions.Consumer;

/**
 * <pre>
 *
 *   @author   :   Alex
 *   @e_mail   :   18238818283@sina.cn
 *   @time     :   2018/01/22
 *   @desc     :
 *   @version  :   V 1.0.9
 */

public class MovieListPresent extends BasePresenterMvp<MovieBean,MovieListFragment,MovieModel> {

    @Override
    public MovieModel createModel() {
        return new MovieModel();
    }

    public void dealWithData(){

        if (getMode() == null){
            return;
        }
        getMode().getData().subscribe(this);
    }

    public void innerRead(String key , final int position){

        if (getMode() == null || getView() == null){
            return;
        }
        getMode().innerRead(key).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                getView().notifyItemChange(position);
            }
        });
    }

}
