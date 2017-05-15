package com.liwy.easymusic.controllers.weibo.detail;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.liwy.easymusic.base.BaseActivity;

import com.liwy.easymusic.R;

import butterknife.BindView;


public class WeiboDetailActivity extends BaseActivity<WeiboDetailPresenter> implements WeiboDetailView {

    @BindView(R.id.wv_vote)
    WebView webView;

    ProgressDialog progressDialog;

    private String url = "";


    @Override
    public void initView() {
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("正在加载 %" + 0);
        progressDialog.show();
        webView.setWebViewClient(new WebListener());
        webView.setWebChromeClient(new MyWebChromeClient());
        //启用支持javascript
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
    }

    // init presenter
    @Override
    protected void initPresenter() {
        mPresenter = new WeiboDetailPresenter();
        mPresenter.init(this,this,this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_weibo_detail;
    }

    @Override
    public void initTop(String name){
        initToolbarWithBack(TOOLBAR_MODE_CENTER,name,R.drawable.btn_back,null);
    }

    @Override
    public void loadUrl(String url){
        this.url = url;
        webView.loadUrl(url);
    }
    private class MyWebChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            progressDialog.setMessage("正在加载 %" + newProgress);
            super.onProgressChanged(view, newProgress);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
        }
    }
    private class WebListener extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progressDialog.dismiss();
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            progressDialog.dismiss();
        }
    }
}
