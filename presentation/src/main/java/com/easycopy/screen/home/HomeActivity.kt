package com.easycopy.screen.home

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.os.Handler
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.easycopy.BR
import com.easycopy.R
import com.easycopy.core.di.SchedulerProvider
import com.easycopy.data.Constant
import com.easycopy.databinding.ActivityMainBinding
import com.easycopy.dto.WSDataPublishDto
import com.easycopy.screen.base.BaseActivity
import com.easycopy.screen.home.sub_modules.file_manager.DirReader
import com.easycopy.screen.home.sub_modules.package_manager.AppPackageManager
import com.easycopy.use_case.WSConnector
import com.easycopy.utils.DeviceUtils
import com.fasterxml.jackson.databind.ObjectMapper
import com.nabinbhandari.android.permissions.PermissionHandler
import com.nabinbhandari.android.permissions.Permissions
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.disposables.Disposable
import timber.log.Timber
import javax.inject.Inject


class HomeActivity : BaseActivity<ActivityMainBinding?, HomeViewModel?>(), HomeNavigator {

    lateinit var salt: String

    private val handler: Handler = Handler()

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

    @Inject
    lateinit var appPackageManager: AppPackageManager

    private val wsState = MutableLiveData<String>()

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            Timber.w("Received action:%s", intent.action)
            wsState.value = intent.action

            if (intent.action.equals(Constant.WS_ACTION_CONNECTED)) {
                homeViewModel.init(salt)
                handler.postDelayed({ sendBasicInfoToUI() }, 3000)
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

    override fun onResume() {
        super.onResume()
        val permissions = arrayOf<String>(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        Permissions.check(this /*context*/, permissions, null /*rationale*/, null /*options*/, object : PermissionHandler() {
            override fun onGranted() {
                // do your task.
            }
        })
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

    var s: String = ""

    override fun serveUIRequest(json: String) {

        Timber.w("Request: %s", json)

        Completable.fromAction {
            val dataType = objectMapper.readTree(json).get("type").asText()
            val wsDataPublishDto = WSDataPublishDto()
            wsDataPublishDto.dataType = dataType


            when (dataType) {
                "BASIC" -> {
                    wsDataPublishDto.data = DeviceUtils.getBasicDetailsJson(objectMapper, this)
                    s = objectMapper.writeValueAsString(wsDataPublishDto)
                }

                "FILE_MANAGER" -> {
                    val rootDir = objectMapper.readTree(json).get("rootDir").asText()
                    if (com.easycopy.utils.StringUtils.isEmpty(rootDir))
                        s = objectMapper.writeValueAsString(dirReader.getFilesAtRootDir())
                    else
                        s = objectMapper.writeValueAsString(dirReader.getFilesAtDir(rootDir))
                }

                "GALLERY" -> {
                    s = "{\"id\":\"1\", \"isFile\":\"true\", \"fileName\":\"TEST.txt\", \"fileLength\":\"22 bytes\"}"
                }
                "PACKAGE_MANAGER" -> {
                    wsDataPublishDto.data = appPackageManager.getInstalledApkList()
                    s = objectMapper.writeValueAsString(wsDataPublishDto)
                }

                else -> {
                    s = "Invalid Request..."
                }
            }
        }
                .subscribeOn(schedulerProvider.newThread())
                .observeOn(schedulerProvider.ui())
                .subscribe(object : CompletableObserver {
                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onComplete() {
                        homeViewModel.sendData(s)
                    }

                    override fun onError(e: Throwable) {
                        Timber.w(e)
                    }

                })
    }

    var temp: String = ""
    fun sendBasicInfoToUI() {
        Completable.fromAction {
            val wsDataPublishDto = WSDataPublishDto()
            wsDataPublishDto.dataType = "BASIC"
            wsDataPublishDto.data = DeviceUtils.getBasicDetailsJson(objectMapper, this)
            temp = objectMapper.writeValueAsString(wsDataPublishDto)
        }
                .subscribeOn(schedulerProvider.newThread())
                .observeOn(schedulerProvider.ui())
                .subscribe(object : CompletableObserver {
                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onComplete() {
                        homeViewModel.sendBasicData(temp)
                    }

                    override fun onError(e: Throwable) {
                        Timber.w(e)
                    }

                })
    }

}