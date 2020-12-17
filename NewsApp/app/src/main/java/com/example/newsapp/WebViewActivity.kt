package com.example.newsapp

import android.os.Bundle
import android.view.MenuItem
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity

class WebViewActivity : AppCompatActivity() {
    private var webView: WebView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)
        webView = findViewById(R.id.mWebView)
        val intent = intent
        val url = intent.getStringExtra("URL")
        val mTitle = intent.getStringExtra("TITLE")

        title = mTitle
        webView?.loadUrl(url!!)

        val actionBar = supportActionBar//获取AppCompatActivity的默认的actionBar
        //给actionBar添加返回按钮
        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home->{
                this.finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}