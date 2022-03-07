package com.mediapark.interview.main.home

import android.util.Log
import com.mediapark.interview.base.BasePresenter
import com.mediapark.interview.main.MainActivityInteractor

class HomeFragmentPresenter <V: HomeFragmentMvpView> : BasePresenter<V>() {

    var interactor: MainActivityInteractor? = null

    fun initContent() {
        Log.d(this.javaClass.name,"To be implemented")
    }

}