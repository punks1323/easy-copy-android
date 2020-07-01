package com.easycopy.core.di.builder;


import com.easycopy.screen.home.HomeActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by pankaj on 27,June,2019
 * pankaj.sharma@stellapps.com
 * Stellapps Technologies Private Limited
 * Bangalore, India
 */
@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector
    abstract HomeActivity bindHomeActivity();

   }
