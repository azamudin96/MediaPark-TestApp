package com.mediapark.interview.util

import org.json.JSONException
import org.json.JSONObject

object ParseUtil {
    inline fun <reified F> shortIf(condition: Boolean, trueValue: F, falseValue: F): F {
        return if (condition) trueValue else falseValue
    }

    inline fun <reified T : Enum<T>> safeValueOf(type: String?): T? {
        return try {
            java.lang.Enum.valueOf(T::class.java, type)
        } catch (e: java.lang.Exception) {
            null
        }
    }

    inline fun <reified F> safeParse(_object: Any?): F? {
        return try { if (_object != null && _object is F) {
            _object as F
        } else null } catch (e: Exception) { e.printStackTrace()
            null
        }
    }

//    inline fun <reified F> jsonToObject(json: String?): F? {
//        if (json.isNullOrEmpty())
//            return null
//
//        val listType: Type = object : TypeToken<F>() {}.type
//        return  VolleyManager.getDefaultGson().fromJson(json, listType)
//    }

    inline fun <reified F> getValue(_object: F?, default: F): F {
        return try { _object ?: default }
        catch (e: Exception) { e.printStackTrace()
            default
        }
    }

    fun maskText(value: String) : String {
        var maskedText = ""
        for (i in 0 until value.count()) {
            maskedText += "*"
        }
        return maskedText
    }

    fun <T> limitRange(value: T, min: T? = null, max: T? = null) : T where T: Number, T: Comparable<T> {
        return when {
            min != null && value < min -> min
            max != null && value > max -> max
            else -> value
        }
    }

    fun <T> inLength(array: MutableList<T>,  index: Int = 0): Boolean {
        return (index >= 0 && index < array.size)
    }

    fun <T> arrayValue(array: MutableList<T>,  index: Int = 0, _default: T): T {
        return if (index >= 0 && index < array.size) array[index] else _default
    }

    fun <T> clearArray(array: MutableList<T>,  index: Int = 0)  {
        if (index == 0)
            array.clear()
        else if (index < array.size) {
            val lastIndex = (index).coerceAtMost(array.size - 1)
            val subArray = mutableListOf<T>().apply {
                addAll(array)
            }
            array.clear()
            array.addAll(subArray.subList(0, lastIndex))
        }
    }

    fun removePhonePrefixSymbol(prefix: String): String {
        return if (prefix.isNotEmpty() && prefix.startsWith("+")) {
            prefix.substring(1)
        } else prefix
    }

    fun showDigitOnly(value: String): String {
        return value.replace("[^\\d.]".toRegex(), "")
    }

    // Type -> 0 = value, 1 = percent
    fun getSeekProgress(progress: Int, min: Float, step: Float = 1F, max: Float = 1F, type: Int = 0): Float {
        return when(type) {
            0 -> min + (progress * step)
            1 -> {
                val different = max - min
                val percent = progress * step
                min + (percent / 100 * different)
            }
            else -> 0F
        }
    }

    // Type -> 0 = value, 1 = percent
    fun getSeekMax(step: Float = 1F, min: Float = 0F, max: Float = 1F, type: Int = 0): Int {
        return when(type) {
            0 -> ((max - min) / step).toInt()
            1 -> (100 / step).toInt()
            else -> 0
        }
    }

    fun generateRandomId(length: Int = 10, caseInsensitive: Boolean = true): String {
        var source = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
        source += "0123456789"
        if (!caseInsensitive)
            source += "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toLowerCase()

        return (1..length)
            .map { source.random() }
            .joinToString("")
    }

    fun extractInt(string: String): MutableList<Int> {
        return Regex("[0-9]+").findAll(string)
            .map(MatchResult::value)
            .map {
                it.toInt()
            }
            .toMutableList()
    }

    fun compareIntList(list1: MutableList<Int>, list2: MutableList<Int>): Int {
        val sizeDiff = list1.size - list2.size
        for (i in 0 until list1.size.coerceAtMost(list2.size)) {
            val int1 = list1[i]
            val int2 = list2[i]
            when {
                int1 > int2 -> return 1
                int2 > int1 -> return -1
            }
        }
        return when {
            sizeDiff > 0 -> 1
            sizeDiff < 0 -> -1
            else -> 0
        }
    }

//    fun getMessage(_object: Any?, _default: String): String {
//        val message = getMessage(_object)
//        return if (message.isNotEmpty()) message else _default
//    }

//    fun getMessage(_object : Any?): String {
//        try {
//            if (_object is String)
//                return _object
//            else if (_object is JSONArray) {
//                for (i in 0 until _object.length()) {
//                    try {
//                        val result: String = getMessage(_object[i])
//                        if (result.isNotEmpty()) return result
//                    } catch (e: Exception) {
//                        e.printStackTrace()
//                    }
//                }
//            } else if (_object is JSONObject) {
//                if (_object.has("message")) {
//                    val result: String = getMessage(_object["message"])
//                    if (result.isNotEmpty()) return result
//                }
//
//                val keys = _object.keys()
//                while (keys.hasNext()) {
//                    val keyValue = keys.next()
//                    val result: String = getMessage(_object[keyValue])
//                    if (!TextUtils.isEmpty(result)) return result
//                }
//            }
//
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//        return ""
//    }

    fun parseJSONObject(raw: String?): JSONObject {
        if (raw.isNullOrEmpty()) return JSONObject()
        return try {
            JSONObject(raw)
        } catch (e: JSONException) {
            JSONObject()
        }
    }
}