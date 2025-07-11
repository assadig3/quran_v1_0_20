package com.hag.al_quran

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment

class SettingsFragment : Fragment() {
    private lateinit var prefs: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Toolbar للرجوع

        val toolbar = view.findViewById<androidx.appcompat.widget.Toolbar>(R.id.fragment_toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back) // ✅ أضف هذا السطر دائمًا!
        toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }

        prefs = requireContext().getSharedPreferences("settings", Context.MODE_PRIVATE)

        // عناصر الواجهة
        val langGroup = view.findViewById<RadioGroup>(R.id.languageGroup)
        val themeGroup = view.findViewById<RadioGroup>(R.id.themeGroup)
        val fontGroup = view.findViewById<RadioGroup>(R.id.fontGroup)
        val keepScreenOnSwitch = view.findViewById<Switch>(R.id.keepScreenOnSwitch)
        val qariSpinner = view.findViewById<Spinner>(R.id.qariSpinner)
        val resetButton = view.findViewById<Button>(R.id.resetOnboardingButton)
        val qrImage = view.findViewById<ImageView>(R.id.qrImage)

        // إعداد القيم المحفوظة
        when (prefs.getString("lang", "ar")) {
            "ar" -> langGroup.check(R.id.lang_ar)
            "en" -> langGroup.check(R.id.lang_en)
        }
        when (prefs.getString("theme", "system")) {
            "light" -> themeGroup.check(R.id.theme_light)
            "dark" -> themeGroup.check(R.id.theme_dark)
            else -> themeGroup.check(R.id.theme_system)
        }
        when (prefs.getString("font", "medium")) {
            "small" -> fontGroup.check(R.id.font_small)
            "medium" -> fontGroup.check(R.id.font_medium)
            "large" -> fontGroup.check(R.id.font_large)
        }
        keepScreenOnSwitch.isChecked = prefs.getBoolean("keep_screen_on", false)

        // Spinner القراء
        val qaris = listOf("الحصري", "عبدالباسط", "العفاسي")
        val spinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, qaris)
        qariSpinner.adapter = spinnerAdapter
        qariSpinner.setSelection(prefs.getInt("qari", 0))

        // استماع للتغييرات
        langGroup.setOnCheckedChangeListener { _, checkedId ->
            val lang = if (checkedId == R.id.lang_ar) "ar" else "en"
            prefs.edit().putString("lang", lang).apply()
            Toast.makeText(requireContext(), "تم تغيير اللغة", Toast.LENGTH_SHORT).show()
        }

        themeGroup.setOnCheckedChangeListener { _, checkedId ->
            val theme = when (checkedId) {
                R.id.theme_light -> "light"
                R.id.theme_dark -> "dark"
                else -> "system"
            }
            prefs.edit().putString("theme", theme).apply()
            Toast.makeText(requireContext(), "تم تغيير المظهر", Toast.LENGTH_SHORT).show()
        }

        fontGroup.setOnCheckedChangeListener { _, checkedId ->
            val font = when (checkedId) {
                R.id.font_small -> "small"
                R.id.font_medium -> "medium"
                R.id.font_large -> "large"
                else -> "medium"
            }
            prefs.edit().putString("font", font).apply()
            Toast.makeText(requireContext(), "تم تغيير حجم الخط", Toast.LENGTH_SHORT).show()
        }

        keepScreenOnSwitch.setOnCheckedChangeListener { _, isChecked ->
            prefs.edit().putBoolean("keep_screen_on", isChecked).apply()
        }

        qariSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, v: View?, position: Int, id: Long) {
                prefs.edit().putInt("qari", position).apply()
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        resetButton.setOnClickListener {
            prefs.edit().clear().apply()
            langGroup.check(R.id.lang_ar)
            themeGroup.check(R.id.theme_system)
            fontGroup.check(R.id.font_medium)
            keepScreenOnSwitch.isChecked = false
            qariSpinner.setSelection(0)
            Toast.makeText(requireContext(), "تمت إعادة ضبط الإعدادات!", Toast.LENGTH_SHORT).show()
        }

        qrImage.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.main_content, QRFragment())
                .addToBackStack(null)
                .commit()
        }
    }
}
