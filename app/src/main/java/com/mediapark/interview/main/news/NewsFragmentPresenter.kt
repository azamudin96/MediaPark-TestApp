package com.mediapark.interview.main.news

import Articles
import android.util.Log
import com.mediapark.interview.base.BasePresenter
import com.mediapark.interview.custom.listener.ApiInterface
import com.mediapark.interview.main.MainActivityInteractor
import com.mediapark.interview.modal.ui.CheckListImageText
import com.mediapark.interview.modal.ui.RecyclerItem
import kotlinx.coroutines.*
import java.util.*

class NewsFragmentPresenter <V: NewsFragmentMvpView> : BasePresenter<V>() {

    var interactor: MainActivityInteractor? = null
    var job: Job? = null

    fun initContent() {
        mvpView?.showLoading()
        getAllArticles()
    }

    fun performArticleSelect(url: String, title: String) {
        mvpView?.invokeWebView(url,title)
    }

    private fun displayContent(articlesList : MutableList<Articles>) {
        val data = mutableListOf<RecyclerItem>()
        for (i in articlesList){
            data.add(RecyclerItem(i))
        }
        mvpView?.displayListing(data)
    }

    private fun getAllArticles() {
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = ApiInterface.getInstance().getAllArticles("f4e972bdf9fb60996cbbd01738a7933d",Locale.getDefault().language)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    displayContent(response.body()!!.articles)
                    mvpView?.hideLoading()
                } else {
                    job?.cancel()
                    mvpView?.hideLoading()
                }
            }
        }
    }


}