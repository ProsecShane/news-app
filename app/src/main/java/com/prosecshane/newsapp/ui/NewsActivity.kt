package com.prosecshane.newsapp.ui

import android.os.Bundle
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.prosecshane.newsapp.R
import com.prosecshane.newsapp.ui.news.NewsAdapter
import kotlinx.coroutines.launch

class NewsActivity : AppCompatActivity() {
    private val viewModel: NewsViewModel by viewModels()

    private val newsView: RecyclerView by lazy { findViewById(R.id.newsview) }
    private val searchbarView: EditText by lazy { findViewById(R.id.searchbar) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

        val adapter = NewsAdapter(lifecycleScope, viewModel.news)
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        newsView.adapter = adapter
        newsView.layoutManager = layoutManager
        newsView.addItemDecoration(DividerItemDecoration(this, layoutManager.orientation))

        lifecycleScope.launch {
            viewModel.fetchNews()
        }
    }
}
