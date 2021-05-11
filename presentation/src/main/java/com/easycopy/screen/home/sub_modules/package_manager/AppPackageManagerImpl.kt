package com.easycopy.screen.home.sub_modules.package_manager

import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ArrayNode
import com.fasterxml.jackson.databind.node.ObjectNode


/**
 * @author pankaj
 * @version 1.0
 * @since 2021-05-02
 */
class AppPackageManagerImpl(c: Context, om: ObjectMapper) : AppPackageManager {

    val context: Context = c
    val objectMapper: ObjectMapper = om

    override fun getInstalledApkList(): String {
        val rootNode: ObjectNode = objectMapper.createObjectNode()
        rootNode.set("system", objectMapper.createArrayNode())
        rootNode.set("normal", objectMapper.createArrayNode())

        val intent = Intent(Intent.ACTION_MAIN, null)
        intent.addCategory(Intent.CATEGORY_LAUNCHER)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED
        val resolveInfoList: List<ResolveInfo> = context.packageManager.queryIntentActivities(intent, 0)
        for (resolveInfo in resolveInfoList) {

            val activityInfo = resolveInfo.activityInfo
            val isSystemPackage = isSystemPackage(resolveInfo)

            val objectNode = objectMapper.createObjectNode()
            objectNode.put("name", getAppName(activityInfo.applicationInfo.packageName))
            objectNode.put("package", activityInfo.applicationInfo.packageName)

            if (isSystemPackage) {
                val systemApp: ArrayNode = rootNode.get("system") as ArrayNode
                systemApp.add(objectNode)
            } else {
                val systemApp: ArrayNode = rootNode.get("normal") as ArrayNode
                systemApp.add(objectNode)
            }
        }
        return rootNode.toString()
    }

    private fun isSystemPackage(resolveInfo: ResolveInfo): Boolean {
        return resolveInfo.activityInfo.applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM != 0
    }

    private fun getAppName(ApkPackageName: String?): String? {
        var name = ""
        val applicationInfo: ApplicationInfo
        val packageManager: PackageManager = context.getPackageManager()
        try {
            applicationInfo = packageManager.getApplicationInfo(ApkPackageName, 0)
            if (applicationInfo != null) {
                name = packageManager.getApplicationLabel(applicationInfo) as String
            }
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return name
    }
}