package com.example.newsapp.http

import android.widget.Toast
import com.example.newsapp.app.MyApplication
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

open class BaseObserver<T : BaseResponse> : Observer<T>,ISubscriber<T>{
    private var mToast : Toast? = null
    override fun onSubscribe(d: Disposable) {
        doOnSubscribe(d)
    }

    override fun onNext(t: T) {
        doOnNext(t)
    }

    override fun onError(e: Throwable) {
        if (e is SocketTimeoutException) {
            setError("SocketTimeoutException")
        } else if (e is ConnectException) {
            setError("ConnectException")
        } else if (e is UnknownHostException) {
            setError("UnknownHostException")
        } else {
            val error = e.message
            doOnError(error)
        }
    }

    override fun onComplete() {
        doOnComplete()
    }

    override fun doOnSubscribe(d: Disposable?) {
    }

    override fun doOnError(errorMsg: String?) {

    }

    override fun doOnNext(t: T) {

    }

    override fun doOnComplete() {
    }

    private fun setError(s: String) {
        showToast(s)
        doOnError(s)
        doOnNetError()
    }

    private fun showToast(s: String) {
        if (mToast == null) {
            mToast = Toast.makeText(MyApplication.getInstance(), s, Toast.LENGTH_SHORT)
        } else {
           mToast?.setText(s)
        }
        mToast?.show()
    }

    private fun doOnNetError() {
        //弹框表明网络有问题
    }
}