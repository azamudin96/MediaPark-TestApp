package com.mediapark.interview.custom.listener

import News
import android.view.textclassifier.TextLanguage
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("top-headlines")
    suspend fun getAllArticles(@Query("token") token:String,@Query("lang") language: String) : Response<News>

    @GET("search")
    suspend fun getSearchArticles(@Query("q") search:String,@Query("token") token:String,@Query("lang") language: String) : Response<News>

    companion object {

        var BASE_URL = "https://gnews.io/api/v4/"

        var retrofitService: ApiInterface? = null
        fun getInstance() : ApiInterface {
            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(ApiInterface::class.java)
            }
            return retrofitService!!
        }

    }
}