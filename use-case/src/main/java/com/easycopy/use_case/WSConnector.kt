package com.easycopy.use_case

/**
 * @author pankaj
 * @version 1.0
 * @since 2020-06-29
 */
interface WSConnector {

    fun connect()
    fun subscribe(url: String, subscribeListener: SubscribeListener)
    fun send(url: String, msg: String)
    fun disconnect()

    interface SubscribeListener {
        fun onMessage(string: String)
    }
}