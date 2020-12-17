package com.example.newsapp.http

import okhttp3.Interceptor
import okhttp3.Response

class CustomInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val newBuilder = request.newBuilder()
        newBuilder.addHeader("X-Bce-Signature","AppCode/"+"4633174096a04b959608f4cb830a4b80")
        newBuilder.addHeader("Content-Type","application/json; charset=UTF-8")

        val build = newBuilder.build();
        return chain.proceed(build)
    }
}