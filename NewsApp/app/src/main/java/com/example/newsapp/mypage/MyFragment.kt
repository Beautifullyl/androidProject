package com.example.newsapp.mypage

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.newsapp.R
import com.example.newsapp.utils.SPUtils
import kotlinx.android.synthetic.main.activity_movie_detals.*
import kotlinx.android.synthetic.main.fragment_my.*

class MyFragment: Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_my, container, false)

    }
    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        info_name.text=SPUtils.instance.getString("ACCOUNT")
        //val my=view.findViewById(R.id.my) as TextView
        //my.text = "This is my Fragment"
    }


}




