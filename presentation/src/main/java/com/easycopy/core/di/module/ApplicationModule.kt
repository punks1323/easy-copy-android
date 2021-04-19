package com.easycopy.core.di.module

import android.Manifest
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.easycopy.constants.AppConstants
import com.easycopy.core.di.AppSchedulerProvider
import com.easycopy.core.di.SchedulerProvider
import com.easycopy.core.di.qualifier.AppPermissions
import com.easycopy.core.di.qualifier.ApplicationContext
import com.easycopy.core.di.qualifier.PreferenceInfo
import com.easycopy.data.DataManager
import com.easycopy.data.DataManagerImpl
import com.easycopy.data.local.pref.PrefManager
import com.easycopy.data.local.pref.PrefManagerImpl
import com.easycopy.screen.home.sub_modules.file_manager.DirReader
import com.easycopy.screen.home.sub_modules.file_manager.DirReaderImpl
import dagger.Module
import dagger.Provides
import java.util.*
import javax.inject.Singleton

/**
 * Created by pankaj on 27,June,2019
 * pankaj.sharma@stellapps.com
 * Stellapps Technologies Private Limited
 * Bangalore, India
 */
@Module
class ApplicationModule {
    @Provides
    @Singleton
    @ApplicationContext
    fun provideContext(application: Application): Context {
        return application
    }

    @Provides
    @PreferenceInfo
    fun providesSharedPrefName(): String {
        return AppConstants.PREF_NAME
    }

    @Provides
    @Singleton
    @AppPermissions
    fun providesAppPermissions(): Set<String> {
        val appNeededPermission: MutableSet<String> = HashSet()
        appNeededPermission.add(Manifest.permission.READ_PHONE_STATE)
        appNeededPermission.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        appNeededPermission.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        appNeededPermission.add(Manifest.permission.ACCESS_FINE_LOCATION)
        appNeededPermission.add(Manifest.permission.ACCESS_COARSE_LOCATION)
        return appNeededPermission
    }

    @Provides
    fun provideSchedulerProvider(): SchedulerProvider {
        return AppSchedulerProvider()
    }

    @Provides
    @Singleton
    fun providesSharedPreference(@ApplicationContext context: Context, @PreferenceInfo prefFileName: String?): SharedPreferences {
        return context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun providesPrefManager(sharedPreferences: SharedPreferences): PrefManager {
        return PrefManagerImpl(sharedPreferences)
    }

    @Provides
    @Singleton
    fun providesDataManager(prefManager: PrefManager): DataManager {
        return DataManagerImpl(prefManager)
    }

    @Provides
    @Singleton
    fun providesDirReader(@ApplicationContext context: Context): DirReader {
        return DirReaderImpl(context)
    }
}