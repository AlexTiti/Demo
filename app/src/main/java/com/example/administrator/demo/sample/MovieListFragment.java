package com.example.administrator.demo.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.administrator.demo.R;
import com.example.administrator.sdk.base.recycler_base.BaseRecyclerFrgMvp;
import com.example.administrator.sdk.base.recycler_base.RecyclerMvpView;
import com.example.administrator.sdk.utils.ResourcesUtils;

import butterknife.BindView;

/**
 *
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link MovieListFragment#newInstance} factory method to
 * create an instance of this fragment.
 * @author Administrator
 */
public class MovieListFragment extends BaseRecyclerFrgMvp<MovieListFragment, MovieListPresent> implements RecyclerMvpView<MovieBean> {

    @BindView(R.id.recycler_movie)
    RecyclerView recyclerViewMovie;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private MovieAdapter movieAdapter;

    public static MovieListFragment newInstance() {
        MovieListFragment fragment = new MovieListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected MovieListPresent creatPresent() {
        return new MovieListPresent();
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        getPresent().dealWithData();
    }

    @Override
    protected void onErrorViewClick(View view) {
        movieAdapter.setEmptyView(errorView);
    }

    @Override
    protected void showLoading() {
        movieAdapter.setEmptyView(loadingView);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_movie_list;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {

        toolbar.setTitle("MOVIE");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressedSupport();
            }
        });
        recyclerViewMovie.setLayoutManager(new LinearLayoutManager(getContext()));
        movieAdapter = new MovieAdapter(R.layout.movie_item);
        recyclerViewMovie.setAdapter(movieAdapter);

    }

    private void initHeadView() {
        View view = ResourcesUtils.inflate(R.layout.head_view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        movieAdapter.setHeaderView(view);
    }

    @Override
    public void showLoadMoreData(MovieBean movieBean) {
    }

    @Override
    public void showLoadMoreError(String errorMessage) {
        movieAdapter.setEmptyView(errorView);
    }

    @Override
    public void shoeRefreshData(MovieBean movieBean) {

    }

    @Override
    public void showRefreshError(String errorMessage) {

    }

    @Override
    public void notifyItemChange(int position) {
        movieAdapter.notifyItemChanged(position);

    }

    @Override
    public void showNetWorkError() {

    }

    @Override
    public void loadSuccess(final MovieBean movieBean) {
        movieAdapter = new MovieAdapter(R.layout.movie_item, movieBean.getSubjects());
        initHeadView();
        recyclerViewMovie.setAdapter(movieAdapter);
        movieAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });
    }

    @Override
    public void loadFailed(String s) {
    }

}
