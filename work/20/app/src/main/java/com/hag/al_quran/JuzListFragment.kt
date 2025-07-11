package com.hag.al_quran

import Juz
import android.content.Intent
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class JuzListFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: JuzAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_juz_list, container, false)
        recyclerView = view.findViewById(R.id.juzRecyclerView)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val juzList = listOf(
            Juz(1, "الجزء الأول", 1),
            Juz(2, "الجزء الثاني", 22),
            Juz(3, "الجزء الثالث", 42),
            Juz(4, "الجزء الرابع", 62),
            Juz(5, "الجزء الخامس", 82),
            Juz(6, "الجزء السادس", 102),
            Juz(7, "الجزء السابع", 121),
            Juz(8, "الجزء الثامن", 142),
            Juz(9, "الجزء التاسع", 162),
            Juz(10, "الجزء العاشر", 182),
            Juz(11, "الجزء الحادي عشر", 201),
            Juz(12, "الجزء الثاني عشر", 222),
            Juz(13, "الجزء الثالث عشر", 242),
            Juz(14, "الجزء الرابع عشر", 262),
            Juz(15, "الجزء الخامس عشر", 282),
            Juz(16, "الجزء السادس عشر", 302),
            Juz(17, "الجزء السابع عشر", 322),
            Juz(18, "الجزء الثامن عشر", 342),
            Juz(19, "الجزء التاسع عشر", 362),
            Juz(20, "الجزء العشرون", 382),
            Juz(21, "الجزء الحادي والعشرون", 402),
            Juz(22, "الجزء الثاني والعشرون", 422),
            Juz(23, "الجزء الثالث والعشرون", 442),
            Juz(24, "الجزء الرابع والعشرون", 462),
            Juz(25, "الجزء الخامس والعشرون", 482),
            Juz(26, "الجزء السادس والعشرون", 502),
            Juz(27, "الجزء السابع والعشرون", 522),
            Juz(28, "الجزء الثامن والعشرون", 542),
            Juz(29, "الجزء التاسع والعشرون", 562),
            Juz(30, "الجزء الثلاثون", 582)
        )

        adapter = JuzAdapter(juzList) { juz ->
            val intent = Intent(requireContext(), QuranPageActivity::class.java)
            intent.putExtra("page_number", juz.pageNumber)
            intent.putExtra("juz_name", juz.name)
            startActivity(intent)
        }

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
    }
}
