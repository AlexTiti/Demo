package com.example.administrator.demo.sample;

import com.example.administrator.sdk.base.mvp.Contract;
import com.example.administrator.sdk.helps.RetrofitCreateHelper;
import com.example.administrator.sdk.helps.RxHelper;
import com.example.administrator.sdk.utils.AppUtils;
import com.example.administrator.sdk.utils.db.DBUtils;
import com.example.administrator.sdk.utils.db.config.DBConfig;
import com.example.administrator.sdk.utils.db.config.ItemState;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * <pre>
 *
 *   @author   :   Alex
 *   @e_mail   :   18238818283@sina.cn
 *   @time     :   2018/01/22
 *   @desc     :
 *   @version  :   V 1.0.9
 */

public class MovieModel implements Contract.ModeMvp {


    public Observable<MovieBean> getData() {
        return RetrofitCreateHelper.createApi(Api.class,Api.HOST)
                .getMovieData().compose(RxHelper.<MovieBean>rxSchedulerHelper());
    }

    public Observable<Boolean> innerRead(final String key){
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> e) throws Exception {
                boolean b = DBUtils.getDB(AppUtils.getContext()).insertRead(DBConfig.TABLE_MOVIE,key, ItemState.STATE_IS_READ);
                e.onNext(b);
                e.onComplete();
            }
        }).compose(RxHelper.<Boolean>rxSchedulerHelper());
    }
}
