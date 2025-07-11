package com.hag.al_quran

import Juz
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class JuzAdapter(
    private val juzList: List<Juz>,
    private val onClick: (Juz) -> Unit
) : RecyclerView.Adapter<JuzAdapter.JuzViewHolder>() {
    inner class JuzViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val juzNumberText: TextView = view.findViewById(R.id.juzNumberText)
        val juzNameText: TextView = view.findViewById(R.id.juzNameText)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JuzViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_juz, parent, false)
        return JuzViewHolder(view)
    }
    override fun onBindViewHolder(holder: JuzViewHolder, position: Int) {
        val juz = juzList[position]
        holder.juzNumberText.text = juz.name              // الجزء الأول
        holder.juzNameText.text = "ص ${juz.pageNumber}"   // ص 1

        holder.itemView.setOnClickListener {
            onClick(juz)
        }
    }

    override fun getItemCount(): Int = juzList.size
}
