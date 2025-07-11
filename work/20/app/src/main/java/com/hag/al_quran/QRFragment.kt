package com.hag.al_quran

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import net.glxn.qrgen.android.QRCode

class QRFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // نربط الواجهة (التي تحتوي على Toolbar خاص)
        return inflater.inflate(R.layout.fragment_qr, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val activity = requireActivity() as AppCompatActivity

        // تفعيل تولبار الفراجمنت
        val toolbar = view.findViewById<androidx.appcompat.widget.Toolbar>(R.id.fragment_toolbar)
        activity.setSupportActionBar(toolbar)
        activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        activity.supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back) // أو أيقونتك

        toolbar.setNavigationOnClickListener {
            activity.onBackPressed()
        }
        toolbar.title = "مشاركة عبر رمز QR"

        // توليد كود QR
        val qrImage = view.findViewById<ImageView>(R.id.qrImageView)
        val qrText = "https://play.google.com/store/apps/details?id=com.hag.al_quran"
        val bitmap: Bitmap = QRCode.from(qrText).withSize(500, 500).bitmap()
        qrImage.setImageBitmap(bitmap)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        val activity = requireActivity() as AppCompatActivity
        // استرجع تولبار MainActivity ليكون الرئيسي
        val mainToolbar = activity.findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        activity.setSupportActionBar(mainToolbar)
        // أعد تفعيل زر الهامبرجر مع الـ Drawer
        val drawerLayout = activity.findViewById<androidx.drawerlayout.widget.DrawerLayout>(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(
            activity, drawerLayout, mainToolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
    }

}
