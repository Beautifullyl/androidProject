package com.example.newsapp.entity

//城市信息数据类
data class City(
    val code: String,
    val location: List<Location>,
    val refer: ReferX
)
data class Location(
    val adm1: String,
    val adm2: String,
    val country: String,
    val fxLink: String,
    val id: String,
    val isDst: String,
    val lat: String,
    val lon: String,
    val name: String,
    val rank: String,
    val type: String,
    val tz: String,
    val utcOffset: String
)
data class ReferX(
    val license: List<String>,
    val sources: List<String>
)