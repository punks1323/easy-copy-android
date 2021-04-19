package com.easycopy.screen.home

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.easycopy.BR
import com.easycopy.R
import com.easycopy.core.di.SchedulerProvider
import com.easycopy.data.Constant
import com.easycopy.databinding.ActivityMainBinding
import com.easycopy.screen.base.BaseActivity
import com.easycopy.screen.home.sub_modules.file_manager.DirReader
import com.easycopy.use_case.WSConnector
import com.fasterxml.jackson.databind.ObjectMapper
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.disposables.Disposable
import timber.log.Timber
import javax.inject.Inject


class HomeActivity : BaseActivity<ActivityMainBinding?, HomeViewModel?>(), HomeNavigator {

    lateinit var salt: String

    @Inject
    lateinit var schedulerProvider: SchedulerProvider

    @Inject
    lateinit var homeViewModel: HomeViewModel

    @Inject
    lateinit var wsConnector: WSConnector

    @Inject
    lateinit var objectMapper: ObjectMapper

    @Inject
    lateinit var dirReader: DirReader

    private val wsState = MutableLiveData<String>()

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            Timber.w("Received action:%s", intent.action)
            wsState.value = intent.action

            if (intent.action.equals(Constant.WS_ACTION_CONNECTED)) {
                homeViewModel.init(salt)
            }
        }
    }

    override val bindingVariable: Int
        get() = BR.viewModel

    override val layoutId: Int
        get() = R.layout.activity_main

    override val viewModel: ViewModel
        get() = homeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeViewModel.setNavigator(this)
        init()
    }

    private fun init() {
        val intentFilter = IntentFilter()
        addIntentActions(intentFilter)
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, intentFilter);
        salt = intent.getStringExtra(com.easycopy.utils.Constant.EXTRA_SALT)
        Timber.w("UUID received: %s", salt)
        wsState.observe(this, Observer {
            homeViewModel.state.set(it)
        })

        homeViewModel.connect();

    }

    override fun onDestroy() {
        super.onDestroy()

        val intentFilter = IntentFilter()
        addIntentActions(intentFilter)
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);

    }

    private fun addIntentActions(intentFilter: IntentFilter) {
        intentFilter.addAction(Constant.WS_ACTION_CONNECTED);
        intentFilter.addAction(Constant.WS_ACTION_DISCONNECTED);
        intentFilter.addAction(Constant.WS_ACTION_ERROR);
        intentFilter.addAction(Constant.WS_ACTION_UNKNOWN);
    }

    override fun serveUIRequest(string: String) {
        Completable.fromAction {
            val asText = objectMapper.readTree(string).get("type").asText()

            when (asText) {
                "BASIC" -> {
                    homeViewModel.sendData("{\"id\":\"1\", \"isFile\":\"true\", \"fileName\":\"TEST.txt\", \"fileLength\":\"22 bytes\"}")
                }

                "FILE_MANAGER" -> {
                    homeViewModel.sendData(objectMapper.writeValueAsString(dirReader.getFilesAtRootDir()))
                }

                "GALLERY" -> {
                    homeViewModel.sendData("{\"id\":\"1\", \"isFile\":\"true\", \"fileName\":\"TEST.txt\", \"fileLength\":\"22 bytes\"}")
                }

                else -> {
                    homeViewModel.sendData("Invalid Request...")
                }
            }
        }
                .subscribeOn(schedulerProvider.newThread())
                .observeOn(schedulerProvider.ui())
                .subscribe(object : CompletableObserver {
                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onComplete() {

                    }

                    override fun onError(e: Throwable) {
                        Timber.w(e)
                    }

                })

    }

}