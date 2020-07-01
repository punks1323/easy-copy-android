package com.easycopy.use_case

import com.easycopy.use_case.model.ConnectionStatus

/**
 * @author pankaj
 * @version 1.0
 * @since 2020-06-29
 */
interface WSConnector {

    fun connect(connectionListener: ConnectionListener)
    fun subscribe(url: String, subscribeListener: SubscribeListener)
    fun send(url: String, msg: String)
    fun disconnect()

    interface ConnectionListener {
        fun connectionCallback(connectionStatus: ConnectionStatus)
    }

    interface SubscribeListener {
        fun onMessage(string: String)
    }
}