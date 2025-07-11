package com.hag.al_quran

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class QuranPagesAdapter(
    private val context: Context,
    private val pageNumbers: List<Int>,
    private val onAyahClick: (surah: Int, ayah: Int) -> Unit
) : RecyclerView.Adapter<QuranPagesAdapter.PageViewHolder>() {

    inner class PageViewHolder(val recyclerView: RecyclerView) : RecyclerView.ViewHolder(recyclerView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PageViewHolder {
        val recyclerView = RecyclerView(context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            layoutManager = LinearLayoutManager(context)
        }
        return PageViewHolder(recyclerView)
    }

    override fun onBindViewHolder(holder: PageViewHolder, position: Int) {
        val pageNumber = pageNumbers[position]
        val pageName = "page_${pageNumber}.webp"

        val adapter = AssetPageAdapter(
            context = context,
            pages = listOf(pageName),
            realPageNumber = pageNumber, // ✅ المفتاح الصحيح
            onAyahClick = onAyahClick,
            onImageTap = {}
        )
        holder.recyclerView.adapter = adapter
    }

    override fun getItemCount(): Int = pageNumbers.size
}
