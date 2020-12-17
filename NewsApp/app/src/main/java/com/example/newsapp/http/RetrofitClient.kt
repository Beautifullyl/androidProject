package com.example.newsapp.http

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RetrofitClient private constructor() {
    private val mRetrofitBuilder: Retrofit.Builder
    private val mOkhttpBuilder: OkHttpClient.Builder = HttpClient.instance?.getBuilder()!!
    val retrofit: Retrofit
        get() = mRetrofitBuilder
            .baseUrl(BaseURL)
            .client(mOkhttpBuilder.build())
            .build()

    companion object {
        var instance: RetrofitClient? = null
            get() {
                if (field == null) {
                    synchronized(RetrofitClient::class.java) {
                        if (field == null) {
                            field = RetrofitClient()
                        }
                    }
                }
                return field
            }
            private set
        var BaseURL = "http://v.juhe.cn/toutiao/"
    }

    init {
        mRetrofitBuilder = Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(mOkhttpBuilder.build())
    }
}