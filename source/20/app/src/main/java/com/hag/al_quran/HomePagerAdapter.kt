package com.hag.al_quran

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class HomePagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2  // عدد التبويبات (يمكنك زيادتها لو عندك أكثر من تبويب)

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> SurahListFragment()   // التبويب الأول: قائمة السور
            1 -> JuzListFragment()     // التبويب الثاني: قائمة الأجزاء
            // إذا أضفت تبويبات أخرى، أضفها هنا مثلاً:
            // 2 -> FavoriteFragment()
            else -> SurahListFragment()
        }
    }
}
