package com.easycopy.screen.base

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import java.lang.ref.WeakReference

/**
 * Created by pankaj on 27,June,2019
 * pankaj.sharma@stellapps.com
 * Stellapps Technologies Private Limited
 * Bangalore, India
 */
abstract class BaseViewModel<N : BaseNavigator?> : ViewModel() {
    val compositeDisposable = CompositeDisposable()
    private var mNavigator: WeakReference<N>? = null
    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }

    val navigator: N?
        get() = mNavigator!!.get()

    fun setNavigator(navigator: N) {
        mNavigator = WeakReference(navigator)
    }
}