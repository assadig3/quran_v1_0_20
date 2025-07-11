package com.hag.al_quran

data class Surah(
    val number: Int,       // رقم السورة
    val name: String,      // اسم السورة
    val type: String,      // مكية/مدنية
    val ayahCount: Int,    // عدد الآيات
    val pageNumber: Int    // رقم أول صفحة للسورة
)
