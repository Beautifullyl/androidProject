package com.example.newsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.moviespage.MovieFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_news.*

class MainActivity : AppCompatActivity(){
    //private lateinit var newslist: ArrayList<News>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = resources.getString(R.string.news)
        loadFragment(com.example.newsapp.newspage.NewsFragment())
        //Toast.makeText(this, tabname, Toast.LENGTH_SHORT).show()
        //Log.d("MainActivity1111","传递来的数据是---$tabname")
        //val bottomNavigation= findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNavigation.setOnNavigationItemSelectedListener{
            when(it.itemId){
                R.id.nav_news -> {
                    title = resources.getString(R.string.news)
                    loadFragment(com.example.newsapp.newspage.NewsFragment())
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.nav_movie -> {
                    title = resources.getString(R.string.movie)
                    loadFragment(MovieFragment())
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.nav_weather -> {
                    title = resources.getString(R.string.weather)
                    loadFragment(com.example.newsapp.weatherpage.WeatherFragment())
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.nav_my -> {
                    title = resources.getString(R.string.my)
                    loadFragment(com.example.newsapp.mypage.MyFragment())
                    return@setOnNavigationItemSelectedListener true
                }

            }
            false
        }



    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }


}