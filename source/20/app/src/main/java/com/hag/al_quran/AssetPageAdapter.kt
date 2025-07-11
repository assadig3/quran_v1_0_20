package com.hag.al_quran

import android.content.*
import android.content.res.Configuration
import android.graphics.*
import android.util.Log
import android.view.*
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.github.chrisbanes.photoview.PhotoView
import com.hag.al_quran.audio.AudioPlayer
import com.hag.al_quran.audio.MadaniPageProvider
import org.json.JSONArray
import org.json.JSONObject
import java.io.InputStream

class AssetPageAdapter(
    private val context: Context,
    private val pages: List<String>,
    private val realPageNumber: Int, // ✅ هام جدا
    private val onAyahClick: (surah: Int, ayah: Int) -> Unit,
    private val onImageTap: () -> Unit
) : RecyclerView.Adapter<AssetPageAdapter.PageViewHolder>() {

    var selectedAyah: Pair<Int, Int>? = null

    private val madaniProvider = MadaniPageProvider(context)
    private val currentQari = madaniProvider.getQariById("muaiqly")

    class PageViewHolder(
        val root: FrameLayout,
        val photoView: PhotoView,
        val ayahOverlay: FrameLayout
    ) : RecyclerView.ViewHolder(root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PageViewHolder {
        val root = FrameLayout(parent.context).apply {
            layoutParams = FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        }

        val photoView = PhotoView(parent.context).apply {
            layoutParams = FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            scaleType = ImageView.ScaleType.CENTER_CROP
        }
        root.addView(photoView)

        val ayahOverlay = FrameLayout(parent.context).apply {
            layoutParams = FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            setBackgroundColor(Color.TRANSPARENT)
        }
        root.addView(ayahOverlay)

        photoView.setOnClickListener { onImageTap() }

        return PageViewHolder(root, photoView, ayahOverlay)
    }

    override fun onBindViewHolder(holder: PageViewHolder, position: Int) {
        val pageName = pages.first()
        try {
            val inputStream: InputStream = context.assets.open("pages/$pageName")
            val bitmap = BitmapFactory.decodeStream(inputStream)

            // ✅ قص الهوامش البيضاء تلقائيًا
            val croppedBitmap = bitmap?.let { cropWhiteMargins(it) }
            holder.photoView.setImageBitmap(croppedBitmap)

            val isNight = (context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) ==
                    Configuration.UI_MODE_NIGHT_YES
            if (isNight) {
                holder.photoView.setColorFilter(Color.argb(100, 0, 0, 0), PorterDuff.Mode.DARKEN)
            } else {
                holder.photoView.clearColorFilter()
            }

            holder.photoView.setTranslationX(0f)
            holder.photoView.setTranslationY(0f)

            val orientation = context.resources.configuration.orientation
            if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                holder.photoView.scaleType = ImageView.ScaleType.CENTER_INSIDE
                holder.photoView.maximumScale = 5f
                holder.photoView.minimumScale = 1f
                holder.photoView.mediumScale = 2f

                holder.photoView.post {
                    holder.photoView.setScale(2f, false)
                }
            } else {
                holder.photoView.scrollTo(0, 0)
            }

            val pageNumber = realPageNumber
            val ayahs = getAyahsForPage(context, pageNumber)
            val totalAyahs = ayahs.size

            holder.ayahOverlay.removeAllViews()

            holder.photoView.post {
                val imgHeight = holder.photoView.height
                val imgWidth = holder.photoView.width
                val ayahHeight = if (totalAyahs > 0) imgHeight / totalAyahs else imgHeight

                ayahs.forEachIndexed { index, (surah, ayah) ->
                    val ayahView = View(context)
                    val params = FrameLayout.LayoutParams(imgWidth, ayahHeight)
                    params.topMargin = index * ayahHeight
                    ayahView.layoutParams = params

                    if (selectedAyah?.first == surah && selectedAyah?.second == ayah) {
                        ayahView.setBackgroundColor(Color.argb(100, 0, 150, 136))
                    } else {
                        ayahView.setBackgroundColor(Color.TRANSPARENT)
                    }

                    ayahView.setOnClickListener {
                        Log.d("AyahClick", "تم الضغط على: سورة $surah، آية $ayah")
                        selectedAyah = surah to ayah
                        notifyDataSetChanged()
                        onAyahClick(surah, ayah)

                        val ayahText = getAyahText(context, surah, ayah)
                        val surahName = getSurahName(context, surah)
                        showAyahOptionsDialog(context, surah, ayah, surahName, ayahText)
                    }

                    holder.ayahOverlay.addView(ayahView)
                }
            }

        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(context, "خطأ في تحميل الصفحة: $pageName", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int = pages.size

    // ====== دالة قص الهوامش البيضاء بذكاء =======
    private fun cropWhiteMargins(source: Bitmap): Bitmap {
        val width = source.width
        val height = source.height
        var top = 0
        var left = 0
        var right = width - 1
        var bottom = height - 1
        var found = false

        // Crop from top
        loop@ for (y in 0 until height) {
            for (x in 0 until width) {
                if (!isWhiteOrNearWhite(source.getPixel(x, y))) {
                    top = y
                    found = true
                    break@loop
                }
            }
        }
        // Crop from bottom
        found = false
        loop@ for (y in height - 1 downTo 0) {
            for (x in 0 until width) {
                if (!isWhiteOrNearWhite(source.getPixel(x, y))) {
                    bottom = y
                    found = true
                    break@loop
                }
            }
        }
        // Crop from left
        found = false
        loop@ for (x in 0 until width) {
            for (y in top..bottom) {
                if (!isWhiteOrNearWhite(source.getPixel(x, y))) {
                    left = x
                    found = true
                    break@loop
                }
            }
        }
        // Crop from right
        found = false
        loop@ for (x in width - 1 downTo 0) {
            for (y in top..bottom) {
                if (!isWhiteOrNearWhite(source.getPixel(x, y))) {
                    right = x
                    found = true
                    break@loop
                }
            }
        }

        // إذا لم يتم العثور على حواف غير بيضاء، أرجع الصورة الأصلية
        if (right <= left || bottom <= top) return source

        return Bitmap.createBitmap(source, left, top, right - left + 1, bottom - top + 1)
    }

    // اعتبر اللون الأبيض وكل الألوان القريبة من الأبيض كأنها "هوامش"
    private fun isWhiteOrNearWhite(pixel: Int): Boolean {
        val r = Color.red(pixel)
        val g = Color.green(pixel)
        val b = Color.blue(pixel)
        // اعتبر كل الألوان الفاتحة جدًا كأنها بيضاء (يمكنك تعديل القيمة 235 حسب الحاجة)
        return r > 235 && g > 235 && b > 235
    }

    // ... باقي الدوال كما هي
    private fun showAyahOptionsDialog(
        context: Context,
        surah: Int,
        ayah: Int,
        surahName: String,
        ayahText: String
    ) {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.floating_ayah_options, null)
        val dialog = android.app.AlertDialog.Builder(context)
            .setView(dialogView)
            .create()

        val fullText = "سورة $surahName - آية $ayah\n\n$ayahText"
        dialogView.findViewById<TextView>(R.id.ayahPreview).text = fullText

        dialogView.findViewById<ImageButton>(R.id.btnClose).setOnClickListener { dialog.dismiss() }

        dialogView.findViewById<ImageButton>(R.id.btnCopyAyah).setOnClickListener {
            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("آية", fullText)
            clipboard.setPrimaryClip(clip)
            Toast.makeText(context, "تم نسخ الآية", Toast.LENGTH_SHORT).show()
        }

        dialogView.findViewById<ImageButton>(R.id.btnShareAyah).setOnClickListener {
            val sendIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, fullText)
                type = "text/plain"
            }
            context.startActivity(Intent.createChooser(sendIntent, "مشاركة عبر"))
        }

        dialogView.findViewById<ImageButton>(R.id.btnTafsir).setOnClickListener {
            Toast.makeText(context, "عرض التفسير قريبًا", Toast.LENGTH_SHORT).show()
        }

        dialogView.findViewById<ImageButton>(R.id.btnPlayAyah).setOnClickListener {
            val url = madaniProvider.getAyahUrl(currentQari!!, surah, ayah)
            AudioPlayer.play(context, url)
            Toast.makeText(context, "جاري تشغيل التلاوة...", Toast.LENGTH_SHORT).show()
        }

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.show()
    }

    private fun getAyahsForPage(context: Context, pageNumber: Int): List<Pair<Int, Int>> {
        val inputStream = context.assets.open("page_ayahs_map.json")
        val jsonString = inputStream.bufferedReader().use { it.readText() }
        val json = JSONObject(jsonString)
        val ayahList = mutableListOf<Pair<Int, Int>>()
        if (!json.has(pageNumber.toString())) return ayahList
        val ayahArray = json.getJSONArray(pageNumber.toString())
        for (i in 0 until ayahArray.length()) {
            val item = ayahArray.getJSONArray(i)
            val surah = item.getInt(0)
            val ayah = item.getInt(1)
            val correctedSurah = surah.coerceIn(1, 114)
            ayahList.add(correctedSurah to ayah)
        }
        return ayahList
    }

    private fun getAyahText(context: Context, surah: Int, ayah: Int): String {
        val inputStream = context.assets.open("quran.json")
        val jsonStr = inputStream.bufferedReader().use { it.readText() }
        val quranArr = JSONArray(jsonStr)

        for (i in 0 until quranArr.length()) {
            val surahObj = quranArr.getJSONObject(i)
            if (surahObj.getInt("id") == surah) {
                val versesArr = surahObj.getJSONArray("verses")
                for (j in 0 until versesArr.length()) {
                    val verseObj = versesArr.getJSONObject(j)
                    if (verseObj.getInt("id") == ayah) {
                        val text = verseObj.getString("text")
                        Log.d("AyahDebug", "Fetched text: $text")
                        return text
                    }
                }
            }
        }
        return "الآية غير موجودة"
    }

    private fun getSurahName(context: Context, surahId: Int): String {
        val inputStream = context.assets.open("quran.json")
        val jsonStr = inputStream.bufferedReader().use { it.readText() }
        val jsonArr = JSONArray(jsonStr)
        for (i in 0 until jsonArr.length()) {
            val surah = jsonArr.getJSONObject(i)
            if (surah.getInt("id") == surahId) {
                return surah.getString("name")
            }
        }
        return "بدون اسم"
    }
}
