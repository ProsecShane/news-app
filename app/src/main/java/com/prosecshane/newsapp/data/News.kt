package com.prosecshane.newsapp.data

import android.graphics.Bitmap

const val AMOUNT_CAP = 50

data class News(
    val articles: List<Article>
) {
    data class Article(
        val urlToImage: String,
        val title: String,
        val description: String,
    )

    data class LoadedArticle(
        val image: Bitmap?,
        val title: String,
        val description: String,
    )
}
