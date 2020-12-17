package com.example.newsapp.newspage

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.*
import com.example.newsapp.adapter.NewsRvAdapter
import com.example.newsapp.entity.DataBean
import com.example.newsapp.service.ApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class NewsChildFragment : Fragment(), NewsRvAdapter.newsItemClick{
    private var newsRv : RecyclerView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflate = inflater.inflate(R.layout.fragment_news_child, container, false)
        newsRv = inflate.findViewById(R.id.news_rv)
        val arguments = arguments
        val type = arguments?.getString("name", "top")
        loadNews(type)
        return inflate
    }

    @SuppressLint("CheckResult")
    private fun loadNews(type:String?) {
//        retrofit.getNews(type, "af2d37d2ed31f7a074f1d49b5460a0b5")?.enqueue(object : Callback<NewsEntity>{
//            override fun onResponse(call: Call<NewsEntity>, response: Response<NewsEntity>) {
//                if(response.isSuccessful){
//                    Log.e("52HZ",""+response.body()?.result?.data?.size)
//                    val adapter = NewsRvAdapter(response.body()?.result?.data)
//                    newsRv?.adapter = adapter
//                }
//            }
//
//            override fun onFailure(call: Call<NewsEntity>, t: Throwable) {
//                Toast.makeText(context,t.message,Toast.LENGTH_SHORT).show()
//            }
//
//        })

        ApiService.instance()?.getNews(type,"7e18309d17af7ed527d517e515e988c6")
            ?.subscribeOn(Schedulers.newThread())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe{
                val data = it.result?.data
                val adapter = NewsRvAdapter(data)
                adapter.setItemClick(this)
                newsRv?.adapter = adapter
            }


    }

    override fun onNewsItemClick(dataBean: DataBean) {
        val intent = Intent(activity,WebViewActivity::class.java)
        intent.putExtra("URL",dataBean.url)
        intent.putExtra("TITLE",dataBean.title)
        activity?.startActivity(intent)
    }

}