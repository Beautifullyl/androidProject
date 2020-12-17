package com.example.newsapp.adapter

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.newsapp.newspage.NewsChildFragment

class NewsViewPagerAdapter(private val context: Context?, fm: FragmentManager) : FragmentPagerAdapter(fm) {
    private val tabList =  mutableListOf<String>("头条", "社会", "国内", "国际", "娱乐", "体育", "军事", "科技", "财经")
    override fun getCount(): Int {
        return tabList.size
    }

    override fun getItem(position: Int): Fragment {
        var newsFragment = NewsChildFragment()
        var bundle =  Bundle()
        when(tabList[position]){
            "头条" -> {
                bundle.putString("name", "top")
            }
            "社会" -> {
                bundle.putString("name", "shehui")
            }
            "国内" -> {
                bundle.putString("name", "guonei")
            }
            "国际" -> {
                bundle.putString("name", "guoji")
            }
            "娱乐" -> {
                bundle.putString("name", "yule")
            }
            "体育" -> {
                bundle.putString("name", "tiyu")
            }
            "军事" -> {
                bundle.putString("name", "junshi")
            }
            "科技" -> {
                bundle.putString("name", "keji")
            }
            "财经" -> {
                bundle.putString("name", "caijing")
            }
            "时尚" -> {
                bundle.putString("name", "shishang")
            }
        }

        newsFragment.arguments = bundle
        return newsFragment
        return newsFragment
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return tabList[position]
    }
}