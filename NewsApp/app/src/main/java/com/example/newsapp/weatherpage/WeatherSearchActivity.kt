package com.example.newsapp.weatherpage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.newsapp.R
import com.example.newsapp.entity.City
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_weather.*
import kotlinx.android.synthetic.main.weather_search.*
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.ArrayList
import kotlin.concurrent.thread

// 天气-搜索城市页面
class WeatherSearchActivity : AppCompatActivity(), View.OnClickListener{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.weather_search)

        //打开onClick监听器
        buttonQuery.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v){
            //点击查询按钮，将textView中的值进行传参
            //如果输入框为空，提示重新输入
            buttonQuery ->{
                val content = searchCity.text.toString()//获取输入框里的城市名
                if (content.isNotEmpty()){
                    getLocationID(content)
                }else{//若输入框为空
                    Toast.makeText(this,"请重新输入",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    //获取网络数据-城市ID
    fun getLocationID(content:String){
        thread {
            var connection: HttpURLConnection? = null
            try {
                val response = StringBuilder()
                val url = URL("https://geoapi.qweather.com/v2/city/lookup?location=$content&key=5b059a81a7594afd87935b9c2fa4ff0a")
                connection = url.openConnection() as HttpURLConnection
                connection.connectTimeout = 8000
                connection.readTimeout = 8000
                connection.connect()
                //对获取到的输入流进行读取
                val input = connection.inputStream
                val reader = BufferedReader(InputStreamReader(input))
                reader.use {
                    reader.forEachLine {
                        response.append(it)
                    }
                }
                val responseString = response.toString()//接收网络请求返回的结果
                Log.d("WeatherSearchActivity", responseString)//验证请求是否成功，显示在控制台
                val anayCity = parseHaveHeaderJArray(responseString)//gson解析函数
                runOnUiThread(Runnable {
                    if (anayCity.code=="200"){
                        val intent = Intent()
                        intent.putExtra("searchLocationID",anayCity.location[0].id)
                        intent.putExtra("isLocationChange",true)
                        intent.putExtra("cityName",anayCity.location[0].name)
                        setResult(RESULT_OK,intent)
                        finish()
                    }else{
                        Toast.makeText(this,"输入城市有误！请检查后重新输入",Toast.LENGTH_SHORT).show()
                    }
                })
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                connection?.disconnect()
            }
        }
    }

    //解析Json数据-转为实体类City
    fun parseHaveHeaderJArray(responseString:String) : City {
        //直接Gson解析
        val gson= Gson()
        return gson.fromJson(responseString,City::class.java)
    }
}
