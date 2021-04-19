package com.easycopy

import android.app.Application
import com.easycopy.core.di.component.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import timber.log.Timber
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

        Timber.plant(object : Timber.DebugTree() {
            override fun createStackElementTag(element: StackTraceElement): String? {
                return "(${element.fileName}:${element.lineNumber})#${element.methodName}"
            }
        })

        DaggerApplicationComponent.builder().application(this).build()
                .inject(this)

    }

    override fun androidInjector(): AndroidInjector<Any> {
        return dispatchingAndroidInjector
    }
}