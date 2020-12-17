package com.example.newsapp.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object DateUtils {
    /* //时间戳转换日期 */
    fun stampToTime(milSecond: Long): String {
        val date = Date(milSecond * 1000)
        val format = SimpleDateFormat("yyyy-MM-dd")
        return format.format(date)
    }

    /* //日期转换为时间戳 */
    fun timeToStamp(timers: String?): Long {
        var d = Date()
        var timeStemp: Long = 0
        try {
            val sf = SimpleDateFormat("yyyy-MM-dd")
            d = sf.parse(timers) // 日期转换为时间戳
        } catch (e: ParseException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }
        timeStemp = d.time
        return timeStemp
    }

    fun dateDiff(startTime: String?, endTime: String?, format: String?): Long {
        // 按照传入的格式生成一个simpledateformate对象
        val sd = SimpleDateFormat(format)
        val nd = (1000 * 24 * 60 * 60).toLong() // 一天的毫秒数
        val nh = (1000 * 60 * 60).toLong() // 一小时的毫秒数
        val nm = (1000 * 60).toLong() // 一分钟的毫秒数
        val ns: Long = 1000 // 一秒钟的毫秒数
        val diff: Long
        var day: Long = 0
        try {
            // 获得两个时间的毫秒时间差异
            diff = (sd.parse(endTime).time
                    - sd.parse(startTime).time)
            day = diff / nd // 计算差多少天
            val hour = diff % nd / nh // 计算差多少小时
            val min = diff % nd % nh / nm // 计算差多少分钟
            val sec = diff % nd % nh % nm / ns // 计算差多少秒
            // 输出结果
            println(
                "时间相差：" + day + "天" + hour + "小时" + min
                        + "分钟" + sec + "秒。"
            )
            return if (day >= 1) {
                day
            } else {
                if (day == 0L) {
                    1
                } else {
                    0
                }
            }
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return 0
    }
}