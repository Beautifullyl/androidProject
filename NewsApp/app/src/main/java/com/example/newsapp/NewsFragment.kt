package com.example.newsapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_news_content.*
import kotlinx.android.synthetic.main.fragment_news.*
import kotlinx.android.synthetic.main.fragment_newscontent.*

class NewsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_news, container, false)
    }
    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val newslist=ArrayList<News>()
        repeat(2) {
            newslist.add(News("news1","news1", R.drawable.news))
            newslist.add(News("news2","news1", R.drawable.movie_icon))
            newslist.add(News("news3","news1", R.drawable.movie_icon))
            newslist.add(News("news4","news1", R.drawable.movie_icon))

        }
//        val myAdapter = NewsAdapter(newslist)
//        Newsrecyclerview.adapter = myAdapter
//        Newsrecyclerview.layoutManager = LinearLayoutManager(context)
        Newsrecyclerview.layoutManager = LinearLayoutManager(this.activity)
        val adapter = NewsAdapter(newslist)
        Newsrecyclerview.adapter =  adapter
        //val news=view.findViewById(R.id.news) as TextView
    }
}
class News(val title:String,val content:String,val imageId:Int){

}
class NewsContentFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_newscontent, container, false)
    }
    fun refresh(title:String,content:String,imageId:Int){
        contentLayout.visibility=View.VISIBLE
        newsdetailsTitle.text=title
        newsdetailsContent.text=content
        newsdetailsImageId.setImageResource(imageId)
    }
}
class NewsContentActivity:AppCompatActivity(){
    companion object{
        fun actionStart(context: Context,title: String,content: String,imageId: Int){
            val intent= Intent(context,NewsContentActivity::class.java).apply{
                putExtra("news_title",title)
                putExtra("news_content",content)
                putExtra("news_imgId",imageId)
            }
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_content)
        val title=intent.getStringExtra("news_title")
        val content=intent.getStringExtra("news_content")
        val imgId=intent.getIntExtra("news_imgId",R.drawable.news)
        if(title!=null && content!=null){
            val fragment=newsContentFrag as NewsContentFragment
            fragment.refresh(title,content,imgId)
        }
    }
}
class NewsAdapter(val newsList:List<News>):RecyclerView.Adapter<NewsAdapter.ViewHolder>(){
    inner class ViewHolder(view:View):RecyclerView.ViewHolder(view){
        val newsImage:ImageView=view.findViewById(R.id.newsImage)
        val newsContent:TextView=view.findViewById(R.id.newsContent)
        val newsTitle:TextView=view.findViewById(R.id.newsTitle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.news_item,parent,false)
        val holder=ViewHolder(view)
        holder.itemView.setOnClickListener{
            val news=newsList[holder.adapterPosition]
            NewsContentActivity.actionStart(parent.context,news.title,news.content,news.imageId)
        }
        return holder
    }

    override fun getItemCount()=newsList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val news=newsList[position]
        holder.newsImage.setImageResource(news.imageId)
        holder.newsContent.text = news.content
        holder.newsTitle.text = news.title
    }
}