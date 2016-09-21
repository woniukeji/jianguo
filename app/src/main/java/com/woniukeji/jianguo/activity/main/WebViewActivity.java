package com.woniukeji.jianguo.activity.main;

import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.woniukeji.jianguo.R;
import com.woniukeji.jianguo.activity.BaseActivity;

import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.OnClick;

public class WebViewActivity extends BaseActivity {

    @BindView(R.id.img_back) ImageView imgBack;
    @BindView(R.id.tv_title) TextView tvTitle;
    @BindView(R.id.img_share) ImageView imgShare;
    @BindView(R.id.tv_loading) ProgressBar tvLoading;
    @BindView(R.id.webview) WebView webview;


    @Override
    public void setContentView() {
        setContentView(R.layout.activity_web_view);
        ButterKnife.bind(this);
    }

    @Override
    public void initViews() {

    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initData() {
        webview.setWebViewClient(webViewClient);
        webview.setWebChromeClient(webChromeClient);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setLoadWithOverviewMode(true);
        webview.getSettings().setSupportZoom(true);
// 设置出现缩放工具
        webview.getSettings().setBuiltInZoomControls(true);
//扩大比例的缩放
        webview.getSettings().setUseWideViewPort(true);
        String url = getIntent().getStringExtra("url");
        webview.loadUrl(url);
    }

    @Override
    public void addActivity() {

    }


    WebViewClient webViewClient = new WebViewClient() {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            webview.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }

        public void onReceivedError(WebView view, int errorCode,
                                    String description, String failingUrl) {
            Log.i("WebViewActivity", "load failed.errorCode is " + errorCode
                    + ",description is " + description);

        }

        ;
    };

    WebChromeClient webChromeClient = new WebChromeClient() {
        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            tvTitle.setText(title);
        }

    };



    @OnClick(R.id.img_back)
    public void onClick() {
      finish();
    }

    @Override
    public void onClick(View v) {

    }
}
