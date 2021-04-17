//adapter for recycleView
package com.example.news

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class NewsListAdapter(private val listener:NewsItemClick): RecyclerView.Adapter<NewsViewHolder>() { //Adapter class extending RecyclerView.Adapter<viewHolder>

    private val item:ArrayList<News> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.item_news,parent,false) //converting xml file into view
        val viewholder=NewsViewHolder(view) //instance of NewsViewHolder() class
        view.setOnClickListener { //handling clicks
            listener.onItemClick(item[viewholder.adapterPosition]) //to extract the position of viewHolder in adapter
        }
        return viewholder
    }

    override fun getItemCount(): Int {
        return item.size
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val currentItem=item[position]
        holder.titleView.text=currentItem.title
        holder.author.text=currentItem.author
        Glide.with(holder.itemView.context).load(currentItem.imageUrl).into(holder.image)
    }

    fun updateNews(updateNews: ArrayList<News>){
        item.clear()
        item.addAll(updateNews)
        notifyDataSetChanged()
    }
}
class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) { //viewHolder class
    val titleView:TextView=itemView.findViewById(R.id.tittle) //extracting the item xml file using it's i'd
    val author:TextView=itemView.findViewById(R.id.author)
    val image:ImageView=itemView.findViewById(R.id.image)
}
interface NewsItemClick{ //interface for call back because clickes should be handled by Activity
    fun onItemClick(item:News)
}
