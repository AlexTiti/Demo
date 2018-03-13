package com.example.administrator.demo.sample.webview;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebBackForwardList;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.example.administrator.demo.R;
import com.example.administrator.sdk.base.mvp.BaseActivityMvp;
import com.example.administrator.sdk.base.mvp.BasePresenterMvp;
import com.example.administrator.sdk.base.mvp.Contract;
import com.example.administrator.sdk.utils.AppUtils;
import com.example.administrator.sdk.utils.network.NetworkConnectionUtils;
import butterknife.BindView;


/**
 * Created by Horrarndoo on 2017/9/20.
 * <p>
 */

public abstract class BaseWebViewLoadActivity<V extends Contract.ViewMvp,P extends BasePresenterMvp>   extends BaseActivityMvp<V,P> {

    @BindView(R.id.tv_detail_title)
    TextView tvDetailTitle;
    @BindView(R.id.iv_detail)
    ImageView ivDetail;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.tv_detail_copyright)
    TextView tvDetailcopyright;
    @BindView(R.id.nswv_detail_content)
    NestedScrollWebView nswvDetailContent;
    @BindView(R.id.pb_web)
    ProgressBar pvWeb;

    private int downX, downY;
    private String mImgurl;

    @Override
    protected void initView(Bundle savedInstanceState) {
        initTitleBar(toolbar, "跳转中...");
        initWebSetting(nswvDetailContent.getSettings());
        initWebView();
        loadDetail();
    }

    @Override
    public void onBackPressedSupport() {
        if (nswvDetailContent.canGoBack()) {
            //获取webView的浏览记录
            WebBackForwardList mWebBackForwardList = nswvDetailContent.copyBackForwardList();
            //这里的判断是为了让页面在有上一个页面的情况下，跳转到上一个html页面，而不是退出当前activity
            if (mWebBackForwardList.getCurrentIndex() > 0) {
                nswvDetailContent.goBack();
                return;
            }
        }
        super.onBackPressedSupport();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_detail;
    }





    /**
     * js接口
     */
    public class SupportJavascriptInterface {
        private Context context;

        public SupportJavascriptInterface(Context context) {
            this.context = context;
        }

        @JavascriptInterface
        public void openImage(final String img) {
            AppUtils.runOnUIThread(new Runnable() {
                @Override
                public void run() {

                }
            });
        }
    }

    protected void initWebView() {
        // 添加js交互接口类，并起别名 imagelistner
        nswvDetailContent.addJavascriptInterface(new SupportJavascriptInterface(this),
                "imagelistner");
        nswvDetailContent.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
            @Override
            public void onPageFinished(WebView view, String url) {
                view.getSettings().setJavaScriptEnabled(true);
                super.onPageFinished(view, url);
                // html加载完成之后，添加监听图片的点击js函数
                addWebImageClickListner(view);
                toolbar.setTitle(getToolbarTitle());
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                view.getSettings().setJavaScriptEnabled(true);
                super.onPageStarted(view, url, favicon);
            }

            // 注入js函数监听
            protected void addWebImageClickListner(WebView webView) {
                // 这段js函数的功能就是，遍历所有的img节点，并添加onclick函数，
                // 函数的功能是在图片点击的时候调用本地java接口并传递url过去
                webView.loadUrl("javascript:(function(){" +
                        "var objs = document.getElementsByTagName(\"img\"); " +
                        "for(var i=0;i<objs.length;i++)  " +
                        "{"
                        + "    objs[i].onclick=function()  " +
                        "    {  "
                        + "        window.imagelistner.openImage(this.src);  " +
                        "    }  " +
                        "}" +
                        "})()");
            }
        });

        nswvDetailContent.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    pvWeb.setVisibility(View.GONE);
                } else {
                    pvWeb.setVisibility(View.VISIBLE);
                    pvWeb.setProgress(newProgress);
                }
            }
        });

        nswvDetailContent.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                WebView.HitTestResult result = ((WebView) v).getHitTestResult();
                if (null == result) {
                    return false;
                }

//                mPresenter.imageLongClicked(result);
                mImgurl = result.getExtra();

                return true;
            }
        });

        nswvDetailContent.setOnTouchListener(WebViewOnTouchListener);
    }

    View.OnTouchListener WebViewOnTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            downX = (int) event.getX();
            downY = (int) event.getY();
            return false;
        }
    };

    /**
     * 初始化WebSetting
     *
     * @param settings WebSetting
     */
    protected void initWebSetting(WebSettings settings) {
        // 缩放至屏幕的大小
        settings.setLoadWithOverviewMode(true);
        // 保存表单数据
        settings.setSaveFormData(true);
        // 是否应该支持使用其屏幕缩放控件和手势缩放
        settings.setSupportZoom(true);
        //        //是否支持手势缩放控制
        //        settings.setBuiltInZoomControls(true);
        //        是否隐藏原生的缩放控件
        //        settings.setDisplayZoomControls(false);
        // 启动应用缓存
        settings.setAppCacheEnabled(true);
        // 排版适应屏幕，只显示一列
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
            //   settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        //  页面加载好以后，再放开图片
        settings.setBlockNetworkImage(false);
        // 使用localStorage则必须打开
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        // WebView启用JavaScript执行。默认的是false。
        settings.setJavaScriptEnabled(true);
        // 设置支持javascript脚本
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        if (NetworkConnectionUtils.isConnected(mContext)) {
            settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        } else {
            settings.setCacheMode(WebSettings.LOAD_CACHE_ONLY);
        }
    }

    /**
     * 加载详情，交由子类实现
     */
    protected abstract void loadDetail();

    /**
     * 返回title，子类实现
     *
     * @return
     */
    protected abstract String getToolbarTitle();
}
