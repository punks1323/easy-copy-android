package com.easycopy.core.di;

import io.reactivex.Scheduler;

/**
 * Created by pankaj on 27,June,2019
 * pankaj.sharma@stellapps.com
 * Stellapps Technologies Private Limited
 * Bangalore, India
 */
public interface SchedulerProvider {

    Scheduler computation();

    Scheduler io();

    Scheduler ui();

    Scheduler newThread();

    Scheduler currentThread();
}
