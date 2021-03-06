package com.example.newsapp.entity

//七天预报天气数据类
data class SevenWeather(
    val code: String,
    val daily: List<Daily>,
    val fxLink: String,
    val refer: ReferXX,
    val updateTime: String
)
data class Daily(
    val cloud: String,
    val fxDate: String,
    val humidity: String,
    val iconDay: String,
    val iconNight: String,
    val moonPhase: String,
    val moonrise: String,
    val moonset: String,
    val precip: String,
    val pressure: String,
    val sunrise: String,
    val sunset: String,
    val tempMax: String,
    val tempMin: String,
    val textDay: String,
    val textNight: String,
    val uvIndex: String,
    val vis: String,
    val wind360Day: String,
    val wind360Night: String,
    val windDirDay: String,
    val windDirNight: String,
    val windScaleDay: String,
    val windScaleNight: String,
    val windSpeedDay: String,
    val windSpeedNight: String
)
data class ReferXX(
    val license: List<String>,
    val sources: List<String>
)