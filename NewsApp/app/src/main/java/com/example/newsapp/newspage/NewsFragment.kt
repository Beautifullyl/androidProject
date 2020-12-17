package com.example.newsapp.newspage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.newsapp.R
import com.example.newsapp.adapter.NewsViewPagerAdapter
import com.google.android.material.tabs.TabLayout

class NewsFragment : Fragment() {
    private var viewPager : ViewPager? = null
    private var tabLayout : TabLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflate = inflater.inflate(R.layout.fragment_news2, container, false)
        viewPager = inflate.findViewById(R.id.news_viewpager)
        tabLayout = inflate.findViewById(R.id.tabLayout)
        val adapter = NewsViewPagerAdapter(activity,childFragmentManager)
        viewPager?.adapter = adapter
        tabLayout?.setupWithViewPager(viewPager)
        return inflate
    }


    override fun onStart() {
        super.onStart()
    }
}