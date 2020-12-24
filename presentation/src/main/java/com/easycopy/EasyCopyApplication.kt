package com.easycopy

import android.app.Application
import com.easycopy.core.di.component.DaggerApplicationComponent
import com.easycopy.utils.InitServer
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import timber.log.Timber
import timber.log.Timber.DebugTree
import javax.inject.Inject

/**
 * @author pankaj
 * @version 1.0
 * @since 2020-05-31
 */
class EasyCopyApplication : Application(), HasAndroidInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()
        Timber.plant(DebugTree())

        DaggerApplicationComponent.builder().application(this).build()
                .inject(this)

        val a = InitServer(8081);


    }

    override fun androidInjector(): AndroidInjector<Any> {
        return dispatchingAndroidInjector
    }
}