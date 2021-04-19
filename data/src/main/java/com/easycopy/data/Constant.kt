package com.easycopy.data

/**
 * @author pankaj
 * @version 1.0
 * @since 2021-04-04
 */

class Constant {
    companion object {

        const val BASE_URL = "ws://192.168.0.128:8081/easy-copy-websocket/websocket"
        const val URL_PREFIX = "/easy-copy/"
        const val TOPIC = "/topic/"

        /* Connection status */
        const val WS_ACTION_CONNECTED = "com.easycopy.ws.WS_ACTION_CONNECTED"
        const val FAILED_SERVER_HEARTBEAT = "com.easycopy.ws.FAILED_SERVER_HEARTBEAT"
        const val WS_ACTION_DISCONNECTED = "com.easycopy.ws.WS_ACTION_DISCONNECTED"
        const val WS_ACTION_ERROR = "com.easycopy.ws.WS_ACTION_ERROR"
        const val WS_ACTION_UNKNOWN = "com.easycopy.ws.WS_ACTION_UNKNOWN"

    }
}