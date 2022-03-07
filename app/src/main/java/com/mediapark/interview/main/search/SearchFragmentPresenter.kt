package com.mediapark.interview.main.search

import Articles
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.mediapark.interview.base.BasePresenter
import com.mediapark.interview.custom.listener.ApiInterface
import com.mediapark.interview.main.MainActivityInteractor
import com.mediapark.interview.modal.ui.RecyclerItem
import kotlinx.coroutines.*
import java.util.*

class SearchFragmentPresenter <V: SearchFragmentMvpView> : BasePresenter<V>() {

    var interactor: MainActivityInteractor? = null
    var job: Job? = null

    fun initContent() {
        Log.d(this.javaClass.name,"To be implemented")
    }

    fun performSearch(searchKeyword:String){
        val context = mvpView?.appContext() ?: return
        mvpView?.showLoading()
        getSearchArticles(searchKeyword,context)
        mvpView?.hideKeyboard()
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

    private fun getSearchArticles(searchKeyword:String,context: Context) {
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = ApiInterface.getInstance().getSearchArticles(searchKeyword,"f4e972bdf9fb60996cbbd01738a7933d",
                Locale.getDefault().language)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    if(response.body()!!.articles.isNotEmpty()){
                        displayContent(response.body()!!.articles)
                    } else {
                        mvpView?.showMessage("No Articles found!")
                    }
                    mvpView?.hideLoading()
                } else {
                    job?.cancel()
                    mvpView?.hideLoading()
                }
            }
        }
    }
}