package com.example.administrator.demo.sample;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseViewHolder;

import com.example.administrator.demo.R;
import com.example.administrator.sdk.base.recycler.BaseRecyclerViewAdapter;
import com.example.administrator.sdk.utils.db.DBUtils;
import com.example.administrator.sdk.utils.db.config.DBConfig;
import com.example.administrator.sdk.utils.db.config.ItemState;

import java.util.List;

/**
 * <pre>
 *
 *   @author   :   Alex
 *   @e_mail   :   18238818283@sina.cn
 *   @time     :   2018/01/22
 *   @desc     :
 *   @version  :   V 1.0.9
 */

public class MovieAdapter extends BaseRecyclerViewAdapter<SubjectsBean, BaseViewHolder> {


    public MovieAdapter(int layoutResId, @Nullable List<SubjectsBean> data) {
        super(layoutResId, data);
    }

    public MovieAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, SubjectsBean item) {
        if (DBUtils.getDB(mContext).isRead(DBConfig.TABLE_MOVIE,item.getId(), ItemState.STATE_IS_READ)){
            helper.setTextColor(R.id.text_name, Color.GRAY);
        }else {
            helper.setTextColor(R.id.text_name, Color.BLACK);
        }
        helper.setText(R.id.text_name, item.getTitle())
                .setText(R.id.show_leader, item.getDirectors().get(0).getName())
                .setText(R.id.show_actor, item.getCasts().get(0).getName())
                .setText(R.id.show_score, String.valueOf(item.getRating().getAverage()));

        RequestOptions options = new RequestOptions();
        options.placeholder(R.drawable.img_default_meizi)
                .error(R.drawable.img_default_meizi)
                .fitCenter();
        Glide.with(mContext).load(item.getImages().getLarge())
                .apply(options)
                .into((ImageView) helper.getView(R.id.imageView2));


    }
}
