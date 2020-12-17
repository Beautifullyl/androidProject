package com.example.newsapp.entity

data class MovieDetailEntity(
    val msg: String,
    val result: DetailsResult,
    val status: Int
)

data class DetailsResult(
    val actor: String,
    val `class`: String,
    val country: String,
    val director: String,
    val duration: String,
    val enname: String,
    val movieid: String,
    val moviename: String,
    val pic: String,
    val publisher: String,
    val releasedate: String,
    val screentype: String,
    val screenwriter: String,
    val summary: String,
    val year: String
)