package com.prosecshane.newsapp.ui

import android.graphics.BitmapFactory
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prosecshane.newsapp.data.AMOUNT_CAP
import com.prosecshane.newsapp.data.News
import com.prosecshane.newsapp.data.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.URL

class NewsViewModel() : ViewModel() {
    private val _news: MutableStateFlow<List<News.LoadedArticle>> = MutableStateFlow(listOf())
    val news: StateFlow<List<News.LoadedArticle>> = _news.asStateFlow()

    fun fetchNews() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                RetrofitInstance.api.getNews().enqueue(object : Callback<News> {
                    override fun onResponse(call: Call<News?>, response: Response<News?>) {
                        val receivedNews = response.body() ?: return
                        val articles = receivedNews.articles.slice(0 until AMOUNT_CAP).map { a ->
                            val image = BitmapFactory.decodeStream(URL(a.urlToImage).openStream())
                            News.LoadedArticle(image, a.title, a.description)
                        }

                        _news.value = articles
                    }

                    override fun onFailure(call: Call<News?>, t: Throwable) {
                        _news.value = listOf()
                    }
                })
            }
        }
    }
}
