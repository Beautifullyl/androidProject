package com.example.newsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = resources.getString(R.string.news)
        loadFragment(NewsFragment())
        //val bottomNavigation= findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNavigation.setOnNavigationItemSelectedListener{
            when(it.itemId){
                R.id.nav_news -> {
                    title = resources.getString(R.string.news)
                    loadFragment(NewsFragment())
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.nav_movie -> {
                    title = resources.getString(R.string.movie)
                    loadFragment(MovieFragment())
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.nav_weather -> {
                    title = resources.getString(R.string.weather)
                    loadFragment(WeatherFragment())
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.nav_my -> {
                    title = resources.getString(R.string.my)
                    loadFragment(MyFragment())
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