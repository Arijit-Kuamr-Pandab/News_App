package com.example.news

import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.example.news.MySingleton.Companion.getInstance
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), NewsItemClick {

    private lateinit var mAdapter: NewsListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView.layoutManager=LinearLayoutManager(this) //layoutMamager for recycleView
        fetchData()
        mAdapter  = NewsListAdapter(this)
        recyclerView.adapter=mAdapter
    }
    private fun fetchData(){ //data to be shown in recyclerView
        val url = "https://saurav.tech/NewsAPI/top-headlines/category/health/in.json";
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener{response->
                Log.d("MainActivity","onResponse${response.toString()}");
                Toast.makeText(this,"Redirecting",Toast.LENGTH_LONG).show()
                val newsJsonArray= response.getJSONArray("articles")
                val newsArray=ArrayList<News>()
                for (i in 0 until newsJsonArray.length()) {
                    val newsJsonObject = newsJsonArray.getJSONObject(i)
                    val news=News(
                        newsJsonObject.getString("title"),
                        newsJsonObject.getString("author"),
                        newsJsonObject.getString("url"),
                        newsJsonObject.getString("urlToImage")
                    )
                    newsArray.add(news)
                }
                mAdapter.updateNews(newsArray)
            },
            Response.ErrorListener {error->
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
                Log.d("MainActivity","error : ${error.message.toString()}");
            })

        // Add the request to the RequestQueue.
        getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

    override fun onItemClick(item: News) { //Here, using chrome custom tabs to open the news article in the app
        val builder = CustomTabsIntent.Builder();
        val coolorInt = Color.parseColor("#6495ED")
        builder.setToolbarColor(coolorInt)
        val customTabsIntent = builder.build();
        customTabsIntent.launchUrl(this, Uri.parse(item.url));
    }
}