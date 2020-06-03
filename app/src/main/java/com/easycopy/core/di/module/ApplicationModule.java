package com.easycopy.core.di.module;

import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.easycopy.constants.AppConstants;
import com.easycopy.core.di.AppSchedulerProvider;
import com.easycopy.core.di.SchedulerProvider;
import com.easycopy.core.di.qualifier.AppPermissions;
import com.easycopy.core.di.qualifier.ApplicationContext;
import com.easycopy.core.di.qualifier.PreferenceInfo;
import com.easycopy.data.DataManager;
import com.easycopy.data.DataManagerImpl;
import com.easycopy.data.local.pref.PrefManager;
import com.easycopy.data.local.pref.PrefManagerImpl;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by pankaj on 27,June,2019
 * pankaj.sharma@stellapps.com
 * Stellapps Technologies Private Limited
 * Bangalore, India
 */
@Module
public class ApplicationModule {

    @Provides
    @Singleton
    @ApplicationContext
    Context provideContext(Application application) {
        return application;
    }

    @Provides
    @PreferenceInfo
    String providesSharedPrefName() {
        return AppConstants.PREF_NAME;
    }

    @Provides
    @Singleton
    ObjectMapper providesObjectMapper() {
        return new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Provides
    @Singleton
    @AppPermissions
    Set<String> providesAppPermissions() {
        Set<String> appNeededPermission = new HashSet<>();
        appNeededPermission.add(Manifest.permission.READ_PHONE_STATE);
        appNeededPermission.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        appNeededPermission.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        appNeededPermission.add(Manifest.permission.ACCESS_FINE_LOCATION);
        appNeededPermission.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        return appNeededPermission;
    }

    @Provides
    SchedulerProvider provideSchedulerProvider() {
        return new AppSchedulerProvider();
    }

    @Provides
    @Singleton
    SharedPreferences providesSharedPreference(@ApplicationContext Context context, @PreferenceInfo String prefFileName) {
        return context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE);
    }

    @Provides
    @Singleton
    PrefManager providesPrefManager(SharedPreferences sharedPreferences) {
        return new PrefManagerImpl(sharedPreferences);
    }

    @Provides
    @Singleton
    DataManager providesDataManager(PrefManager prefManager) {
        return new DataManagerImpl(prefManager);
    }
}