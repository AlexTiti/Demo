package com.example.administrator.demo.sample;

import java.io.Serializable;
import java.util.List;

/**
 * <pre>
 *
 *   @author   :   Alex
 *   @e_mail   :   18238818283@sina.cn
 *   @time     :   2018/01/23
 *   @desc     :
 *   @version  :   V 1.0.9
 */

public class SubjectsBean implements Serializable {
    private static final long serialVersionUID = "SubjectsBean".hashCode();
    /**
     * rating : {"max":10,"average":7.5,"stars":"40","min":0}
     * genres : ["剧情","爱情","战争"]
     * title : 无问西东
     * casts : [{"alt":"https://movie.douban.com/celebrity/1041014/","avatars":{"small":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1359895311.0.webp","large":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1359895311.0.webp","medium":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1359895311.0.webp"},"name":"章子怡","id":"1041014"},{"alt":"https://movie.douban.com/celebrity/1041404/","avatars":{"small":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1472787652.32.webp","large":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1472787652.32.webp","medium":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1472787652.32.webp"},"name":"黄晓明","id":"1041404"},{"alt":"https://movie.douban.com/celebrity/1077991/","avatars":{"small":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1453574419.48.webp","large":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1453574419.48.webp","medium":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1453574419.48.webp"},"name":"张震","id":"1077991"}]
     * collect_count : 145619
     * original_title : 无问西东
     * subtype : movie
     * directors : [{"alt":"https://movie.douban.com/celebrity/1313682/","avatars":{"small":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p19485.webp","large":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p19485.webp","medium":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p19485.webp"},"name":"李芳芳","id":"1313682"}]
     * year : 2018
     * images : {"small":"https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2507572275.webp","large":"https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2507572275.webp","medium":"https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2507572275.webp"}
     * alt : https://movie.douban.com/subject/6874741/
     * id : 6874741
     */

    private RatingBean rating;
    private String title;
    private int collect_count;
    private String original_title;
    private String subtype;
    private String year;
    private MovieBean.ImagesBean images;
    private String alt;
    private String id;
    private List<String> genres;
    private List<MovieBean.CastsBean> casts;
    private List<MovieBean.DirectorsBean> directors;

    public RatingBean getRating() {
        return rating;
    }

    public void setRating(RatingBean rating) {
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCollect_count() {
        return collect_count;
    }

    public void setCollect_count(int collect_count) {
        this.collect_count = collect_count;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public MovieBean.ImagesBean getImages() {
        return images;
    }

    public void setImages(MovieBean.ImagesBean images) {
        this.images = images;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public List<MovieBean.CastsBean> getCasts() {
        return casts;
    }

    public void setCasts(List<MovieBean.CastsBean> casts) {
        this.casts = casts;
    }

    public List<MovieBean.DirectorsBean> getDirectors() {
        return directors;
    }

    public void setDirectors(List<MovieBean.DirectorsBean> directors) {
        this.directors = directors;
    }

    public static class RatingBean implements Serializable{
        private static final long serialVersionUID = "RatingBean".hashCode();
        /**
         * max : 10
         * average : 7.5
         * stars : 40
         * min : 0
         */

        private int max;
        private double average;
        private String stars;
        private int min;

        public int getMax() {
            return max;
        }

        public void setMax(int max) {
            this.max = max;
        }

        public double getAverage() {
            return average;
        }

        public void setAverage(double average) {
            this.average = average;
        }

        public String getStars() {
            return stars;
        }

        public void setStars(String stars) {
            this.stars = stars;
        }

        public int getMin() {
            return min;
        }

        public void setMin(int min) {
            this.min = min;
        }
    }
}
