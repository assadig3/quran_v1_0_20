package com.hag.al_quran

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SurahAdapter(
    private val surahList: List<Surah>,
    private val onItemClick: (Surah) -> Unit
) : RecyclerView.Adapter<SurahAdapter.SurahViewHolder>() {

    inner class SurahViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val surahNumber: TextView = itemView.findViewById(R.id.surahNumber)
        val surahTitle: TextView = itemView.findViewById(R.id.surahTitle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SurahViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_surah, parent, false)
        return SurahViewHolder(view)
    }

    override fun onBindViewHolder(holder: SurahViewHolder, position: Int) {
        val surah = surahList[position]
        holder.surahNumber.text = surah.number.toString()
        holder.surahTitle.text = "${surah.name} (${surah.ayahCount} آيات)"
        holder.itemView.setOnClickListener { onItemClick(surah) }
    }

    override fun getItemCount(): Int = surahList.size
}
