package com.prosecshane.newsapp.ui.news

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.recyclerview.widget.RecyclerView
import com.prosecshane.newsapp.R
import com.prosecshane.newsapp.data.News.LoadedArticle
import com.prosecshane.newsapp.ui.news.NewsAdapter.PreviewViewHolder
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NewsAdapter(
    lifecycleScope: LifecycleCoroutineScope,
    private val articlesStateFlow: StateFlow<List<LoadedArticle>>,
) : RecyclerView.Adapter<PreviewViewHolder>() {
    private val articles: MutableList<LoadedArticle> = mutableListOf()

    init {
        lifecycleScope.launch {
            articlesStateFlow.collect {
                articles.clear()
                articles.addAll(it)
                notifyDataSetChanged()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PreviewViewHolder =
        PreviewViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.newscard, parent, false))

    override fun onBindViewHolder(holder: PreviewViewHolder, position: Int) {
        val article = articles[position]

        if (article.image != null) holder.image.setImageBitmap(article.image)
        holder.title.text = article.title
        holder.subtitle.text = article.description
    }

    override fun getItemCount() = articles.size

    class PreviewViewHolder(newsView: View) : RecyclerView.ViewHolder(newsView) {
        val image: ImageView = newsView.findViewById(R.id.preview_image)
        val title: TextView = newsView.findViewById(R.id.preview_title)
        val subtitle: TextView = newsView.findViewById(R.id.preview_subtitle)
    }
}
