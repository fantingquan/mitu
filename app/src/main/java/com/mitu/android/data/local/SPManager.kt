package com.mitu.android.data.local

import com.mitu.android.utils.SPUtils


/**
 * 本地数据管理器
 */
object SPManager {

    private val SP_UTILS = SPUtils.getInstance("MiTao")

    fun putObject(key: String, any: Any?) {
        SP_UTILS.putObject(key, any)
    }

    fun <T> getObject(key: String, clazz: Class<T>): T {
        return SP_UTILS.getObject(key, clazz)
    }

    fun putString(key: String, value: String) {
        SP_UTILS.put(key, value)
    }

    fun getString(key: String): String {
        return SP_UTILS.getString(key)
    }

    fun getString(key: String, default: String): String {
        return SP_UTILS.getString(key, default)
    }

    fun putInt(key: String, value: Int) {
        SP_UTILS.put(key, value)
    }

    fun getInt(key: String): Int {
        return SP_UTILS.getInt(key)
    }

    fun getInt(key: String, defaultValue: Int): Int {
        return SP_UTILS.getInt(key, defaultValue)
    }

    fun putLong(key: String, value: Long) {
        SP_UTILS.put(key, value)
    }

    fun getLong(key: String): Long {
        return SP_UTILS.getLong(key)
    }

    fun putBoolean(key: String, value: Boolean) {
        SP_UTILS.put(key, value)
    }

    fun getBoolean(key: String, defaultBoolean: Boolean): Boolean {
        return SP_UTILS.getBoolean(key, defaultBoolean)
    }

    fun remove(key: String) {
        return SP_UTILS.remove(key, true)
    }

    fun clear() {
        SP_UTILS.clear()
    }

    fun sp2String(): String {
        val sb = StringBuilder()
        val map = SP_UTILS.all
        for ((key, value) in map) {
            sb.append(key)
                    .append(": ")
                    .append(value)
                    .append("\n")
        }
        return sb.toString()
    }

}
