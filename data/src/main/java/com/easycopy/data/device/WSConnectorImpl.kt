package com.easycopy.data.device

import android.content.Context
import android.content.Intent
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.easycopy.data.Constant
import com.easycopy.use_case.WSConnector
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.StompClient
import ua.naiksoftware.stomp.dto.LifecycleEvent
import ua.naiksoftware.stomp.dto.StompHeader


class WSConnectorImpl(private val baseUrl: String, private val context: Context) : WSConnector {

    lateinit var stompClient: StompClient;

    override fun connect() {

        stompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, baseUrl)
        //stompClient.withClientHeartbeat(1000).withServerHeartbeat(1000)

        val dispLifecycle: Disposable = stompClient.lifecycle()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { lifecycleEvent ->
                    Timber.w(lifecycleEvent.toString())
                    Timber.w(lifecycleEvent.type.name)

                    when (lifecycleEvent.type) {
                        LifecycleEvent.Type.OPENED -> {
                            LocalBroadcastManager.getInstance(context).sendBroadcast(Intent(Constant.WS_ACTION_CONNECTED))
                        }
                        LifecycleEvent.Type.ERROR -> {
                            LocalBroadcastManager.getInstance(context).sendBroadcast(Intent(Constant.WS_ACTION_ERROR))
                            Timber.w(lifecycleEvent.exception)
                        }
                        LifecycleEvent.Type.CLOSED -> {
                            LocalBroadcastManager.getInstance(context).sendBroadcast(Intent(Constant.WS_ACTION_DISCONNECTED))
                        }
                        LifecycleEvent.Type.FAILED_SERVER_HEARTBEAT -> {
                            LocalBroadcastManager.getInstance(context).sendBroadcast(Intent(Constant.FAILED_SERVER_HEARTBEAT))
                        }
                        else -> {
                            LocalBroadcastManager.getInstance(context).sendBroadcast(Intent(Constant.WS_ACTION_UNKNOWN))
                        }
                    }
                }

        val headers = listOf(StompHeader("LOGIN", "guest"), StompHeader("PASSCODE", "guest"))
        stompClient.connect(headers);
    }

    override fun send(url: String, msg: String) {
        stompClient.send(url, msg).subscribe({ },
                { t -> Timber.w(t) })
    }

    override fun subscribe(url: String, subscribeListener: WSConnector.SubscribeListener) {
        stompClient.topic(url).subscribe({ topicMessage -> subscribeListener.onMessage(topicMessage.payload) }
        ,{e-> Timber.w(e)})
    }

    override fun disconnect() {
        stompClient.disconnect()
    }

}