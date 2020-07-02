package com.easycopy.core.di

import io.reactivex.Scheduler

/**
 * Created by pankaj on 27,June,2019
 * pankaj.sharma@stellapps.com
 * Stellapps Technologies Private Limited
 * Bangalore, India
 */
interface SchedulerProvider {
    fun computation(): Scheduler
    fun io(): Scheduler
    fun ui(): Scheduler
    fun newThread(): Scheduler
    fun currentThread(): Scheduler
}