package com.example.newsapp.http

import android.util.Log
import com.trello.rxlifecycle2.android.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

class HttpClient private constructor() {
    private val builder: OkHttpClient.Builder
    fun getBuilder(): OkHttpClient.Builder {
        return builder
    }

    companion object {
        var instance: HttpClient? = null
            get() {
                if (field == null) {
                    synchronized(HttpClient::class.java) {
                        if (field == null) {
                            field = HttpClient()
                        }
                    }
                }
                return field
            }
            private set
    }

    init {
        builder = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor { message ->
                if (BuildConfig.DEBUG) {
                    Log.v("retrofitLog==>>", message)
                }
            }.setLevel(HttpLoggingInterceptor.Level.BODY))
            .writeTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(5, TimeUnit.SECONDS)
    }
}