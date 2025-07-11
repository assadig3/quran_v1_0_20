package com.hag.al_quran

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.nav_view)

        // إعداد زر القائمة الجانبية (الهامبرجر)
        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // تحميل الـ Fragment الأساسي
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_content, HomeTabsFragment())
                .commit()
        }

        // تعامل مع عناصر القائمة الجانبية
        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_content, HomeTabsFragment())
                        .commit()
                }
                R.id.settings -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_content, SettingsFragment())
                        .addToBackStack(null)
                        .commit()
                }
                R.id.help -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_content, HelpFragment())
                        .addToBackStack(null)
                        .commit()
                }
                R.id.about -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_content, AboutFragment())
                        .addToBackStack(null)
                        .commit()
                }
                R.id.share -> {
                    // مشاركة التطبيق
                    val shareIntent = android.content.Intent(android.content.Intent.ACTION_SEND)
                    shareIntent.type = "text/plain"
                    shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, "حمّل تطبيق القرآن الكريم من ...")
                    startActivity(android.content.Intent.createChooser(shareIntent, "مشاركة التطبيق"))
                }
                R.id.share_qr -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_content, QRFragment())
                        .addToBackStack(null)
                        .commit()
                }
                R.id.rate -> {
                    openAppInPlayStore()
                }


                R.id.other_apps -> {
                    // فتح صفحة تطبيقاتك الأخرى في المتجر
                    val intent = android.content.Intent(
                        android.content.Intent.ACTION_VIEW,
                        android.net.Uri.parse("https://play.google.com/store/apps/developer?id=afagamro")
                    )
                    startActivity(intent)
                }
                R.id.action_exit -> {
                    showExitDialog() // ✅ هنا تفعيل نافذة تأكيد الخروج
                }
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }

    }

    private fun showExitDialog() {
        val builder = androidx.appcompat.app.AlertDialog.Builder(this)
        builder.setTitle("تأكيد الخروج")
        builder.setMessage("هل تريد الخروج من التطبيق؟")
        builder.setPositiveButton("نعم") { dialog, _ ->
            dialog.dismiss()
            finishAffinity() // إغلاق التطبيق بالكامل
        }
        builder.setNegativeButton("إلغاء") { dialog, _ ->
            dialog.dismiss()
        }
        builder.show()
    }
    fun openAppInPlayStore() {
        val appPackageName = "com.hag.al_quran" // ← عدّل حسب اسم حزمة تطبيقك
        try {
            // محاولة فتح Google Play مباشرةً
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$appPackageName"))
            intent.setPackage("com.android.vending") // يجبر على استخدام Google Play الأصلي
            startActivity(intent)
        } catch (e: Exception) {
            // إذا لم يوجد Google Play، فتح الرابط في المتصفح
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName"))
            startActivity(intent)
        }
    }

    @SuppressLint("GestureBackNavigation")
    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}
