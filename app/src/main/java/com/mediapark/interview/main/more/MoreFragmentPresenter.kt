package com.mediapark.interview.main.more

import android.util.Log
import com.mediapark.interview.base.BasePresenter
import com.mediapark.interview.main.MainActivityInteractor

class MoreFragmentPresenter <V: MoreFragmentMvpView> : BasePresenter<V>() {

    var interactor: MainActivityInteractor? = null

    fun initContent() {
        Log.d(this.javaClass.name,"To be implemented")
    }

}