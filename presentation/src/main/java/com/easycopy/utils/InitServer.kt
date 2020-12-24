package com.easycopy.utils

import fi.iki.elonen.NanoHTTPD

/**
 * @author pankaj
 * @version 1.0
 * @since 2020-12-24
 */
class InitServer(port: Int) : NanoHTTPD(port) {

    init {
        start(NanoHTTPD.SOCKET_READ_TIMEOUT, false)
        System.out.println("\nRunning! Point your browsers to http://localhost:$port/ \n")
    }

    override fun serve(session: IHTTPSession?): Response {
        var msg = "<html><body><h1>Hello server</h1>\n"
        val parms = session!!.parms
        if (parms["username"] == null) {
            msg += "<form action='?' method='get'>\n  <p>Your name: <input type='text' name='username'></p>\n" + "</form>\n"
        } else {
            msg += "<p>Hello, " + parms.get("username") + "!</p>"
        }
        return newFixedLengthResponse("$msg</body></html>\n")
    }
}