package com.example.newsapp.entity

import com.example.newsapp.http.BaseResponse

data class NewsEntity(
    //private var reason: String?,
    //private val error_code: Int?,
    val result: ResultBean?
):BaseResponse()

data class ResultBean(
    var stat: String?,
    val data: List<DataBean>?
)

data class DataBean(
    private var uniquekey: String?,
    val title: String?,
    val date: String?,
    val category: String?,
    val author_name: String?,

    val url: String?,
    val thumbnail_pic_s: String?,
    val thumbnail_pic_s02: String?,
    val thumbnail_pic_s03: String?

)