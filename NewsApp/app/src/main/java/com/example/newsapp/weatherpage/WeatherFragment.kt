package com.example.newsapp.weatherpage

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore.Images.Media.getBitmap
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.entity.NowWeather
import com.example.newsapp.entity.SevenWeather
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_weather.*
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.*
import kotlin.concurrent.thread

// 天气页面
class WeatherFragment: Fragment(), View.OnClickListener{

    private var isLocationChange= false //用于判断位置是否变化，默认不变在北京
    private var locationID = "101010100" //默认北京

    /*接受searchActivity返回的城市名
      并改变相应的全局变量isLocationChange和locationID*/
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
    {super.onActivityResult(requestCode, resultCode, data)
        when( requestCode ){
            1 -> if(resultCode == AppCompatActivity.RESULT_OK){
                locationID = data?.getStringExtra("searchLocationID").toString()
                isLocationChange = data?.getBooleanExtra("isLocationChange",false)!!
                val cityNameChange = data?.getStringExtra("cityName").toString()
                Toast.makeText(activity,"查询成功！",Toast.LENGTH_SHORT).show()
                if (isLocationChange){
                    //println("变了变了！")//验证传ischange成功
                    getNowWeather(locationID)
                    getFutureWeather(locationID)
                    locationName.setText(cityNameChange)//改变布局内城市名
                }
                /*println(locationID)
                println(cityNameChange)*/
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_weather, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //初始化界面，默认地区在北京
        getNowWeather(locationID)
        getFutureWeather(locationID)

        //打开onClick监听器
        locationSearch.setOnClickListener(this)
        currentRefresh.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v){
            //点击位置图标，实现搜索页面跳转(Fragment跳转Activity)
            locationSearch -> {
                val intent = Intent(activity,WeatherSearchActivity::class.java)
                startActivityForResult(intent,1)
            }
            //点击刷新图标，实现页面实时天气数据刷新
            currentRefresh -> {
                getNowWeather(locationID)
            }
        }
    }

    //初始化整个布局
    /*fun initView(){
        //getNowWeather(locationID)
        //getFutureWeather(locationID)
    }*/

    //获取网络数据-实时天气
    fun getNowWeather(locationID:String) {
        thread {
            var connection: HttpURLConnection? = null
            try {
                val response = StringBuilder()
                val url = URL("https://devapi.qweather.com/v7/weather/now?location=$locationID&key=5b059a81a7594afd87935b9c2fa4ff0a")
                connection = url.openConnection() as HttpURLConnection
                connection.connectTimeout = 8000
                connection.readTimeout = 8000
                connection.connect()
                val input = connection.inputStream
                val reader = BufferedReader(InputStreamReader(input))
                reader.use {
                    reader.forEachLine {
                        response.append(it)
                    }
                }
                val responseString = response.toString()//接收网络请求返回的结果
                val anayNowWeather = parseHaveHeaderJArray(responseString)//gson解析函数
                println(anayNowWeather)
                activity?.runOnUiThread(Runnable {
                    nowTemp.setText(anayNowWeather.now.temp)
                    nowWeather.setText(anayNowWeather.now.text)
                    nowWindDir.setText(anayNowWeather.now.windDir)
                    nowWindScale.setText(anayNowWeather.now.windScale+"级")
                    nowWeatherIcon.setImageResource(findWeatherIcon(anayNowWeather.now.icon))
                    lastUpdateTimeTv.setText("上次更新时间："+anayNowWeather.updateTime)
                })
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                connection?.disconnect()
            }
        }
    }
    //Gson解析为实体类NowWeather
    fun parseHaveHeaderJArray(responsestring:String): NowWeather {
        val gson= Gson()
        val nowBean= gson.fromJson(responsestring,NowWeather::class.java)
        return nowBean
    }

    //获取网络数据-实时天气
    fun getFutureWeather(locationID: String) {
        thread {
            var connection: HttpURLConnection? = null
            try {
                val response = StringBuilder()
                val url = URL("https://devapi.qweather.com/v7/weather/7d?location=$locationID&key=5b059a81a7594afd87935b9c2fa4ff0a")
                connection = url.openConnection() as HttpURLConnection
                connection.connectTimeout = 8000
                connection.readTimeout = 8000
                connection.connect()
                //添加请求参数location&KEY
                /*val KEY = "5b059a81a7594afd87935b9c2fa4ff0a"
                val output = DataOutputStream(connection.outputStream)
                output.writeBytes("location=$location&key=$KEY")*/
                //对获取到的输入流进行读取
                val input = connection.inputStream
                val reader = BufferedReader(InputStreamReader(input))
                reader.use {
                    reader.forEachLine {
                        response.append(it)
                    }
                }
                val responseString = response.toString()//接收网络请求返回的结果
                val anaySevenWeather = parseHaveHeaderJArray2(responseString)//gson解析函数
                //println(anaySevenWeather)
                activity?.runOnUiThread(Runnable {
                    dayTemMax.setText(anaySevenWeather.daily[0].tempMax+"℃")
                    dayTemMin.setText(anaySevenWeather.daily[0].tempMin+"℃")
                    val futureWeatherList = ArrayList<FutureWeather>()
                    for (i in 0..6){
                        futureWeatherList.add(FutureWeather(anaySevenWeather.daily[i].fxDate,anaySevenWeather.daily[i].tempMax,anaySevenWeather.daily[i].tempMin,findWeatherIcon(anaySevenWeather.daily[i].iconDay)))

                    }
                    //填充recyclerview
                    val layoutManager = LinearLayoutManager(activity)
                    futureRecyclerView.layoutManager = layoutManager
                    val adapter = WeatherFutureAdapter(futureWeatherList)
                    futureRecyclerView.adapter = adapter
                })
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                connection?.disconnect()
            }
        }
    }
    //Gson解析为实体类SevenWeather
    fun parseHaveHeaderJArray2(responsestring:String): SevenWeather {
        val gson= Gson()
        val sevenBean= gson.fromJson(responsestring,SevenWeather::class.java)
        return sevenBean
    }

    //建立内部类WeatherFutureAdapter，作为RecyclerView的配适器
    inner class WeatherFutureAdapter(val weatherFutureList: List<FutureWeather>):RecyclerView.Adapter<WeatherFutureAdapter.ViewHolder>(){

        inner class ViewHolder(view : View): RecyclerView.ViewHolder(view){
            val futureName : TextView = view.findViewById(R.id.futureName)
            val futureIcon : ImageView = view.findViewById(R.id.futureIcon)
            val futureTemMax : TextView = view.findViewById(R.id.futureTemMax)
            val futureTemMin : TextView = view.findViewById(R.id.futureTemMin)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_future_weather,parent,false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val weatherFuture = weatherFutureList[position]
            holder.futureName.setText(weatherFuture.date)
            holder.futureIcon.setImageResource(weatherFuture.iconDay)
            holder.futureTemMax.setText(weatherFuture.tempMax)
            holder.futureTemMin.setText(weatherFuture.tempMin)

        }

        override fun getItemCount() = weatherFutureList.size
    }

    //配适weather图标
    fun findWeatherIcon(code:String):Int{
        return when(code){
            "100"-> R.mipmap.weather_100
            "101" -> R.mipmap.weather_101
            "102" -> R.mipmap.weather_102
            "103" -> R.mipmap.weather_103
            "104" -> R.mipmap.weather_104
            "150" -> R.mipmap.weather_150
            "153" -> R.mipmap.weather_153
            "154" -> R.mipmap.weather_154
            "300" -> R.mipmap.weather_300
            "301" -> R.mipmap.weather_301
            "302" -> R.mipmap.weather_302
            "305" -> R.mipmap.weather_305
            "306" -> R.mipmap.weather_306
            "307" -> R.mipmap.weather_307
            "308" -> R.mipmap.weather_308
            "309" -> R.mipmap.weather_309
            "310" -> R.mipmap.weather_310
            "311" -> R.mipmap.weather_311
            "312" -> R.mipmap.weather_312
            "313" -> R.mipmap.weather_313
            "314" -> R.mipmap.weather_314
            "315" -> R.mipmap.weather_315
            "316" -> R.mipmap.weather_316
            "317" -> R.mipmap.weather_317
            "318" -> R.mipmap.weather_318
            "399" -> R.mipmap.weather_399
            "350" -> R.mipmap.weather_350
            "351" -> R.mipmap.weather_351
            "400" -> R.mipmap.weather_400
            "401" -> R.mipmap.weather_401
            "402" -> R.mipmap.weather_402
            "403" -> R.mipmap.weather_403
            "404" -> R.mipmap.weather_404
            "405" -> R.mipmap.weather_405
            "406" -> R.mipmap.weather_406
            "407" -> R.mipmap.weather_407
            "408" -> R.mipmap.weather_408
            "409" -> R.mipmap.weather_409
            "410" -> R.mipmap.weather_410
            "499" -> R.mipmap.weather_499
            "456" -> R.mipmap.weather_456
            "457" -> R.mipmap.weather_457
            "500" -> R.mipmap.weather_500
            "501" -> R.mipmap.weather_501
            "502" -> R.mipmap.weather_502
            "503" -> R.mipmap.weather_503
            "504" -> R.mipmap.weather_504
            "507" -> R.mipmap.weather_507
            "508" -> R.mipmap.weather_508
            "509" -> R.mipmap.weather_509
            "510" -> R.mipmap.weather_510
            "511" -> R.mipmap.weather_511
            "512" -> R.mipmap.weather_512
            "513" -> R.mipmap.weather_513
            "514" -> R.mipmap.weather_514
            "515" -> R.mipmap.weather_515
            "900" -> R.mipmap.weather_900
            "901" -> R.mipmap.weather_901
            else -> R.mipmap.weather_999
        }
    }
}