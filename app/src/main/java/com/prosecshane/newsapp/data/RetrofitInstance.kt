package com.prosecshane.newsapp.data

import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

private const val BASE_URL = "https://newsapi.org"

object RetrofitInstance {
    private val retrofit by lazy {
        Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient.Builder().callTimeout(1500, TimeUnit.SECONDS).build())
            .build()
    }

    val api by lazy {
        retrofit.create(ApiInterface::class.java)
    }

    interface ApiInterface {
        @GET("/v2/everything")
        fun getNews(
            @Query("apiKey") key: String = "7f88d87551e1431ab9fd7d4142103fe8",
            @Query("pageSize") pageSize: String = "50",
            @Query("page") page: String = "1",
        ): Call<News>
    }
}
