package com.example.newsapp.entity

data class MoviesEntity(
    val msg: String,
    val result: List<Result>,
    val status: Int
)

data class Result(
    val cityid: String,
    val depth: String,
    val name: String,
    val parentid: String,
    val parentname: String,
    val topname: String
)