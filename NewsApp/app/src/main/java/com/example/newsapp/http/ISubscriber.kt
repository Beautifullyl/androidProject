package com.example.newsapp.http

import io.reactivex.disposables.Disposable

interface ISubscriber<T : BaseResponse?> {
    fun doOnSubscribe(d: Disposable?)
    fun doOnError(errorMsg: String?)
    fun doOnNext(t: T)
    fun doOnComplete()
}