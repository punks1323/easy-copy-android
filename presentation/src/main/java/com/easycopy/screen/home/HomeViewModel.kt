package com.easycopy.screen.home

import androidx.databinding.ObservableField
import com.easycopy.data.Constant
import com.easycopy.data.DataManager
import com.easycopy.screen.base.BaseViewModel
import com.easycopy.use_case.WSConnector
import com.fasterxml.jackson.databind.ObjectMapper
import timber.log.Timber
import javax.inject.Inject

/**
 * @author pankaj
 * @version 1.0
 * @since 2020-06-01
 */
class HomeViewModel @Inject constructor(private val objectMapper: ObjectMapper, private val dataManager: DataManager, private val wsConnector: WSConnector) : BaseViewModel<HomeNavigator?>() {

    var state = ObservableField<String>()
    var uuid: String = "";
    var apiKey: String = "";

    fun connect() {
        wsConnector.connect()
    }

    fun init(stringExtra: String?) {
        uuid = objectMapper.readTree(stringExtra).get("uuid").asText();
        apiKey = objectMapper.readTree(stringExtra).get("apiKey").asText();

        wsConnector.send(Constant.URL_PREFIX + uuid + "/authenticate", objectMapper.readTree(stringExtra).toString())
        Timber.w("Yeah!")

        wsConnector.subscribe(Constant.TOPIC + uuid + "/android/question", object : WSConnector.SubscribeListener {
            override fun onMessage(string: String) {
                navigator?.serveUIRequest(string);
            }
        })

    }

    fun onTest() {
        wsConnector.send(Constant.URL_PREFIX + uuid + "/android/answer", apiKey)
    }

    fun sendData(s: String) {
        wsConnector.send(Constant.URL_PREFIX + uuid + "/android/answer", s)
    }

}