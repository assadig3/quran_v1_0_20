package com.hag.al_quran

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SurahListFragment : Fragment() {

    private lateinit var surahRecyclerView: RecyclerView
    private lateinit var adapter: SurahAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_surah_list, container, false)
        surahRecyclerView = view.findViewById(R.id.surahRecyclerView)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val surahList = listOf(
            Surah(1, "الفاتحة", "مكية", 7, 1),
            Surah(2, "البقرة", "مدنية", 286, 2),
            Surah(3, "آل عمران", "مدنية", 200, 50),
            Surah(4, "النساء", "مدنية", 176, 77),
            Surah(5, "المائدة", "مدنية", 120, 106),
            Surah(6, "الأنعام", "مكية", 165, 128),
            Surah(7, "الأعراف", "مكية", 206, 151),
            Surah(8, "الأنفال", "مدنية", 75, 177),
            Surah(9, "التوبة", "مدنية", 129, 187),
            Surah(10, "يونس", "مكية", 109, 208),
            Surah(11, "هود", "مكية", 123, 221),
            Surah(12, "يوسف", "مكية", 111, 235),
            Surah(13, "الرعد", "مدنية", 43, 249),
            Surah(14, "إبراهيم", "مكية", 52, 255),
            Surah(15, "الحجر", "مكية", 99, 262),
            Surah(16, "النحل", "مكية", 128, 267),
            Surah(17, "الإسراء", "مكية", 111, 282),
            Surah(18, "الكهف", "مكية", 110, 293),
            Surah(19, "مريم", "مكية", 98, 305),
            Surah(20, "طه", "مكية", 135, 312),
            Surah(21, "الأنبياء", "مكية", 112, 322),
            Surah(22, "الحج", "مدنية", 78, 332),
            Surah(23, "المؤمنون", "مكية", 118, 342),
            Surah(24, "النور", "مدنية", 64, 350),
            Surah(25, "الفرقان", "مكية", 77, 359),
            Surah(26, "الشعراء", "مكية", 227, 367),
            Surah(27, "النمل", "مكية", 93, 377),
            Surah(28, "القصص", "مكية", 88, 385),
            Surah(29, "العنكبوت", "مكية", 69, 396),
            Surah(30, "الروم", "مكية", 60, 404),
            Surah(31, "لقمان", "مكية", 34, 411),
            Surah(32, "السجدة", "مكية", 30, 415),
            Surah(33, "الأحزاب", "مدنية", 73, 418),
            Surah(34, "سبإ", "مكية", 54, 428),
            Surah(35, "فاطر", "مكية", 45, 434),
            Surah(36, "يس", "مكية", 83, 440),
            Surah(37, "الصافات", "مكية", 182, 446),
            Surah(38, "ص", "مكية", 88, 453),
            Surah(39, "الزمر", "مكية", 75, 458),
            Surah(40, "غافر", "مكية", 85, 467),
            Surah(41, "فصلت", "مكية", 54, 477),
            Surah(42, "الشورى", "مكية", 53, 483),
            Surah(43, "الزخرف", "مكية", 89, 489),
            Surah(44, "الدخان", "مكية", 59, 496),
            Surah(45, "الجاثية", "مكية", 37, 499),
            Surah(46, "الأحقاف", "مكية", 35, 502),
            Surah(47, "محمد", "مدنية", 38, 507),
            Surah(48, "الفتح", "مدنية", 29, 511),
            Surah(49, "الحجرات", "مدنية", 18, 515),
            Surah(50, "ق", "مكية", 45, 518),
            Surah(51, "الذاريات", "مكية", 60, 520),
            Surah(52, "الطور", "مكية", 49, 523),
            Surah(53, "النجم", "مكية", 62, 526),
            Surah(54, "القمر", "مكية", 55, 528),
            Surah(55, "الرحمن", "مدنية", 78, 531),
            Surah(56, "الواقعة", "مكية", 96, 534),
            Surah(57, "الحديد", "مدنية", 29, 537),
            Surah(58, "المجادلة", "مدنية", 22, 542),
            Surah(59, "الحشر", "مدنية", 24, 545),
            Surah(60, "الممتحنة", "مدنية", 13, 549),
            Surah(61, "الصف", "مدنية", 14, 551),
            Surah(62, "الجمعة", "مدنية", 11, 553),
            Surah(63, "المنافقون", "مدنية", 11, 554),
            Surah(64, "التغابن", "مدنية", 18, 556),
            Surah(65, "الطلاق", "مدنية", 12, 558),
            Surah(66, "التحريم", "مدنية", 12, 560),
            Surah(67, "الملك", "مكية", 30, 562),
            Surah(68, "القلم", "مكية", 52, 564),
            Surah(69, "الحاقة", "مكية", 52, 566),
            Surah(70, "المعارج", "مكية", 44, 568),
            Surah(71, "نوح", "مكية", 28, 570),
            Surah(72, "الجن", "مكية", 28, 572),
            Surah(73, "المزمل", "مكية", 20, 574),
            Surah(74, "المدثر", "مكية", 56, 575),
            Surah(75, "القيامة", "مكية", 40, 577),
            Surah(76, "الإنسان", "مدنية", 31, 578),
            Surah(77, "المرسلات", "مكية", 50, 580),
            Surah(78, "النبأ", "مكية", 40, 582),
            Surah(79, "النازعات", "مكية", 46, 583),
            Surah(80, "عبس", "مكية", 42, 585),
            Surah(81, "التكوير", "مكية", 29, 586),
            Surah(82, "الانفطار", "مكية", 19, 587),
            Surah(83, "المطففين", "مكية", 36, 587),
            Surah(84, "الانشقاق", "مكية", 25, 589),
            Surah(85, "البروج", "مكية", 22, 590),
            Surah(86, "الطارق", "مكية", 17, 591),
            Surah(87, "الأعلى", "مكية", 19, 591),
            Surah(88, "الغاشية", "مكية", 26, 592),
            Surah(89, "الفجر", "مكية", 30, 593),
            Surah(90, "البلد", "مكية", 20, 594),
            Surah(91, "الشمس", "مكية", 15, 595),
            Surah(92, "الليل", "مكية", 21, 595),
            Surah(93, "الضحى", "مكية", 11, 596),
            Surah(94, "الشرح", "مكية", 8, 596),
            Surah(95, "التين", "مكية", 8, 597),
            Surah(96, "العلق", "مكية", 19, 597),
            Surah(97, "القدر", "مكية", 5, 598),
            Surah(98, "البينة", "مدنية", 8, 598),
            Surah(99, "الزلزلة", "مدنية", 8, 599),
            Surah(100, "العاديات", "مكية", 11, 599),
            Surah(101, "القارعة", "مكية", 11, 600),
            Surah(102, "التكاثر", "مكية", 8, 600),
            Surah(103, "العصر", "مكية", 3, 601),
            Surah(104, "الهمزة", "مكية", 9, 601),
            Surah(105, "الفيل", "مكية", 5, 602),
            Surah(106, "قريش", "مكية", 4, 602),
            Surah(107, "الماعون", "مكية", 7, 602),
            Surah(108, "الكوثر", "مكية", 3, 603),
            Surah(109, "الكافرون", "مكية", 6, 603),
            Surah(110, "النصر", "مدنية", 3, 603),
            Surah(111, "المسد", "مكية", 5, 604),
            Surah(112, "الإخلاص", "مكية", 4, 604),
            Surah(113, "الفلق", "مكية", 5, 604),
            Surah(114, "الناس", "مكية", 6, 604)
        )

        adapter = SurahAdapter(surahList) { surah ->
            val intent = Intent(context, QuranPageActivity::class.java)
            intent.putExtra("page_number", surah.pageNumber)
            intent.putExtra("surah_name", surah.name) // مرر اسم السورة
            startActivity(intent)

        }

        surahRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        surahRecyclerView.adapter = adapter
    }
}
