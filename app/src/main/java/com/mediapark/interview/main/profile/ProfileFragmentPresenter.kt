package com.mediapark.interview.main.profile

import android.util.Log
import com.mediapark.interview.base.BasePresenter
import com.mediapark.interview.main.MainActivityInteractor

class ProfileFragmentPresenter <V: ProfileFragmentMvpView> : BasePresenter<V>() {

    var interactor: MainActivityInteractor? = null

    fun initContent() {
        Log.d(this.javaClass.name,"To be implemented")
    }

}