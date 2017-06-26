package com.example.davidtran.nytarticlesearch.Activities;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.example.davidtran.nytarticlesearch.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by davidtran on 6/25/17.
 */

public class WebViewActivity extends Activity {
    @BindView(R.id.WebView)
    WebView webView;
    @BindView(R.id.loadingWebBar)
    ProgressBar loadingWebBar;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (webView.canGoBack()) {
                        webView.goBack();
                    } else {
                        finish();
                    }
                    return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        ButterKnife.bind(this);
        final String url = getIntent().getStringExtra("DetailWebViewUrl");

        setUpWebView(url);


    }
    private void setUpWebView(String url){
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                loadingWebBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                loadingWebBar.setVisibility(View.GONE);
            }


        });
        webView.loadUrl(url);
    }
}
