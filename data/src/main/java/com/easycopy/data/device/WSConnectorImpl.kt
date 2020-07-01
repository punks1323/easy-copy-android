package com.easycopy.data.device

import com.easycopy.use_case.WSConnector
import com.easycopy.use_case.model.ConnectionStatus
import io.reactivex.FlowableSubscriber
import org.reactivestreams.Subscription
import timber.log.Timber
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.StompClient
import ua.naiksoftware.stomp.dto.LifecycleEvent
import ua.naiksoftware.stomp.dto.StompHeader
import ua.naiksoftware.stomp.dto.StompMessage

class WSConnectorImpl(private val baseUrl: String) : WSConnector {
    private val msgUploadUrl = "/app/send"
    private val msgDownloadUrl = "/topic/messages"

    // baseUrl;        //ws://192.168.0.108:8080/websocket
    lateinit var stompClient: StompClient;

    override fun connect(connectionListener: WSConnector.ConnectionListener) {

        stompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, baseUrl);
        stompClient.withClientHeartbeat(3000).withServerHeartbeat(3000);

        stompClient.lifecycle()
                .subscribe(object : FlowableSubscriber<LifecycleEvent> {
                    override fun onComplete() {
                        Timber.i("Reading web socket lifecycle completed")
                    }

                    override fun onSubscribe(s: Subscription) {
                        Timber.i("Reading lifecycle for web socket")
                    }

                    override fun onNext(t: LifecycleEvent?) {
                        when (t?.type) {
                            LifecycleEvent.Type.OPENED -> {
                                Timber.d("Stomp connection opened");
                                connectionListener.connectionCallback(ConnectionStatus.CONNECTED)
                            }
                            LifecycleEvent.Type.ERROR -> {
                                Timber.d(t.getException(), "Error");
                                connectionListener.connectionCallback(ConnectionStatus.ERROR)
                            }
                            LifecycleEvent.Type.CLOSED -> {
                                Timber.d("Stomp connection closed");
                                connectionListener.connectionCallback(ConnectionStatus.DISCONNECTED)
                            }
                            else -> {
                                Timber.d("Unknown state %s", t?.type?.name);
                                connectionListener.connectionCallback(ConnectionStatus.UNKNOWN)
                            }
                        }
                    }

                    override fun onError(t: Throwable?) {
                        Timber.e(t)
                    }

                })

        val headers = listOf(StompHeader("LOGIN", "guest"), StompHeader("PASSCODE", "guest"))
        stompClient.connect(headers);
    }

    override fun subscribe(url: String, subscribeListener: WSConnector.SubscribeListener) {

        stompClient.topic(url)
                .subscribe(object : FlowableSubscriber<StompMessage> {
                    override fun onComplete() {
                        Timber.i("Waiting for incoming message: onComplete")
                    }

                    override fun onSubscribe(s: Subscription) {
                        Timber.i("Waiting for incoming message: onSubscribe")
                    }

                    override fun onNext(t: StompMessage?) {
                        Timber.d("Received:: %s", t?.payload)
                    }

                    override fun onError(t: Throwable?) {
                        Timber.e(t)
                    }

                })
    }

    override fun send(url: String, msg: String) {
        stompClient.send(url, msg).subscribe();
    }

    override fun disconnect() {
        stompClient.disconnect()
    }

}