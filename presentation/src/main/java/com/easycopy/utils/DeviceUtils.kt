package com.easycopy.utils

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.graphics.Point
import android.net.ConnectivityManager
import android.os.Build
import com.fasterxml.jackson.databind.ObjectMapper

/**
 * @author pankaj
 * @version 1.0
 * @since 2021-05-11
 */
object DeviceUtils {

    fun getBasicDetailsJson(objectMapper: ObjectMapper, context: Context): String {
        val objectNode = objectMapper.createObjectNode()

        objectNode.put("product", Build.PRODUCT)
        objectNode.put("model", Build.MODEL)
        objectNode.put("manufacturer", Build.MANUFACTURER)
        objectNode.put("serial", Build.SERIAL)
        objectNode.put("radio_version", Build.getRadioVersion())
        objectNode.put("release",Build.VERSION.RELEASE)
        objectNode.put("os_version",System.getProperty("os.version"))
        objectNode.put("wifi_state",isWifiNetworkAvailable(context))
        objectNode.put("internet_status",isMobileNetworkAvailable(context))
        objectNode.put("bluetooth_state",checkBluetoothConnection())
        objectNode.put("screen_resolution",getDeviceScreenResolution(context))
        objectNode.put("fingerprint",Build.FINGERPRINT)

        return objectNode.toString()
    }

    private fun getDeviceScreenResolution(context: Context): String {
        val display = (context as Activity).windowManager.defaultDisplay
        val size = Point();
        display.getSize(size);

        val width = size.x; //device width
        val height = size.y; //device height

        return "$width x $height"; //example "480 * 800"
    }

    /**
     * Checks if the device has Wifi connection.
     */
    private fun isWifiNetworkAvailable(context: Context): String {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val wifiNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (wifiNetwork != null && wifiNetwork.isConnected) {
            return "Connected";
        } else return "Disconnected";
    }

    /**
     * Checks if the device has 3G or other mobile data connection.
     */

    private fun isMobileNetworkAvailable(context: Context): String {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val mobileNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)

        if (mobileNetwork != null && mobileNetwork.isConnected) {
            return "Connected";
        } else {
            return "Disconnected";
        }

    }

    private fun checkBluetoothConnection(): String {
        val mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        if (mBluetoothAdapter == null) {
            return "Not supported";
        } else {
            if (!mBluetoothAdapter.isEnabled) {
                return "OFF";
            } else {
                return "ON";
            }
        }
    }
}