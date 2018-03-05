package com.example.administrator.sdk.base.mvp;

import io.reactivex.Observable;

/**
 * <pre>
 *
 *   @author   :   Alex
 *   @e_mail   :   18238818283@sina.cn
 *   @time     :   2017/12/12
 *   @desc     :
 *   @version  :   V 1.0.9
 */

public interface Contract {

    interface ModeMvp {

    }

    interface ViewMvp<T> {
        /**
         * 请求成功
         *
         * @param t T
         */
        void loadSuccess(T t);

        /**
         * 请求失败
         *
         * @param s 错误信息
         */
        void loadFailed(String s);


    }

    interface PresenterMvp {

    }
}
