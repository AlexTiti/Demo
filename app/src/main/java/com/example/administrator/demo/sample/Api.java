package com.example.administrator.demo.sample;



import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * <pre>
 *
 *   @author   :   Alex
 *   @e_mail   :   18238818283@sina.cn
 *   @time     :   2018/01/22
 *   @desc     :
 *   @version  :   V 1.0.9
 */

public interface Api {
    public final static String HOST = "Https://api.douban.com/";

    /**
     * 获取电影数据
     * @return
     */
    @GET("v2/movie/in_theaters")
    Observable<MovieBean> getMovieData();


}
