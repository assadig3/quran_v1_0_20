package com.hag.al_quran.audio

import android.content.Context
import org.json.JSONObject
import java.io.InputStream

class PageAyahMapLoader(private val context: Context) {

    private val pageMap: JSONObject

    init {
        val jsonStr = loadJSONFromAsset("page_ayahs_map.json")
        pageMap = JSONObject(jsonStr)
    }

    fun getAyahsForPage(pageNumber: Int): List<Pair<Int, Int>> {
        val ayahs = mutableListOf<Pair<Int, Int>>()
        val array = pageMap.optJSONArray(pageNumber.toString()) ?: return ayahs

        for (i in 0 until array.length()) {
            val pair = array.optJSONArray(i)
            if (pair != null && pair.length() == 2) {
                val surah = pair.optInt(0)
                val ayah = pair.optInt(1)
                ayahs.add(surah to ayah)
            }
        }
        return ayahs
    }

    private fun loadJSONFromAsset(filename: String): String {
        val inputStream: InputStream = context.assets.open(filename)
        return inputStream.bufferedReader().use { it.readText() }
    }
}
