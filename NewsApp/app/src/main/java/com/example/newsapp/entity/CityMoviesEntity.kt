package com.example.newsapp.entity

data class CityMoviesEntity(
    val msg: String?,
    val result: MoviesResult?,
    val status: Int
)

data class MoviesResult(
    val city: String?,
    val cityid: String?,
    val date: String?,
    val list: List<ResultEntity>?
)

data class ResultEntity(
    val movieid: String?,
    val moviename: String?,
    val pic: String?
)