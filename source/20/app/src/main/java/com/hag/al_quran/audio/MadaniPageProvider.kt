package com.hag.al_quran.audio

import android.content.Context
import com.hag.al_quran.model.Qari

class MadaniPageProvider(private val context: Context) {

    private val qaris: List<Qari> = listOf(
        Qari("fares", "فارس عباد", "https://everyayah.com/data/Fares_Abbad_64kbps/"),
        Qari("sudais", "السديس", "https://everyayah.com/data/Abdurrahmaan_As-Sudais_64kbps/"),
        Qari("shuraym", "الشريم", "https://everyayah.com/data/Saood_ash-Shuraym_64kbps/"),
        Qari("muaiqly", "المعيقلي", "https://everyayah.com/data/Maher_AlMuaiqly_64kbps/")
    )

    fun getQaris(): List<Qari> = qaris

    fun getQariById(id: String): Qari? {
        return qaris.find { it.id == id }
    }

    fun getAyahUrl(qari: Qari, surah: Int, ayah: Int): String {
        val surahStr = surah.toString().padStart(3, '0')
        val ayahStr = ayah.toString().padStart(3, '0')
        return "${qari.url}$surahStr$ayahStr.mp3"
    }
}
