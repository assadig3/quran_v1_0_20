package com.hag.al_quran

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2

class QuranAssetFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_quran_asset, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val viewPager = view.findViewById<ViewPager2>(R.id.quranPagesViewPager)

        val pageNumbers = (1..604).toList()

        viewPager.adapter = QuranPagesAdapter(
            requireContext(),
            pageNumbers,
            onAyahClick = { surah, ayah ->
                Toast.makeText(requireContext(), "سورة $surah - آية $ayah", Toast.LENGTH_SHORT).show()
            }
        )
    }
}
