package com.example.davidtran.nytarticlesearch.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.davidtran.nytarticlesearch.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by davidtran on 6/25/17.
 */

public class WebViewActivity extends Activity {
    @BindView(R.id.WebView)
    WebView webView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        ButterKnife.bind(this);
        final String url = getIntent().getStringExtra("DetailWebViewUrl");
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);
    }
}
