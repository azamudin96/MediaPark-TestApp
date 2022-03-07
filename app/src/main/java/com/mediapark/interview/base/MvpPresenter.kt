package com.mediapark.interview.base

interface MvpPresenter<V : MvpView?> {
    fun onAttach(mvpView: V)
    fun onDetach()
}