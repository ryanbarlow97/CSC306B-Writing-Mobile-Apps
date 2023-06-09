package com.rbarlow.csc306

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import java.text.SimpleDateFormat
import java.util.Locale

class BookmarkAdapter(private val dataSet: List<Item>) :
    RecyclerView.Adapter<BookmarkAdapter.ViewHolder>() {

    private lateinit var context: Context
    private var onItemClickListener: OnItemClickListener? = null

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val titleTextView: TextView = view.findViewById(R.id.bookmark_title)
        private val descriptionTextView: TextView = view.findViewById(R.id.bookmark_description)
        private val timestampTextView: TextView = view.findViewById(R.id.bookmark_timestamp)
        private val bookmarkImage: ImageView = view.findViewById(R.id.bookmark_image)

        fun bind(item: Item) {
            titleTextView.text = item.name
            descriptionTextView.text = item.description

            val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val timestamp = formatter.format(item.addedOn)
            val timestampText = context.getString(R.string.bookmarked_on, timestamp)
            timestampTextView.text = timestampText

            // Load image
            Glide.with(context)
                .load(item.image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(bookmarkImage)

            // Set click listener
            itemView.setOnClickListener {
                onItemClickListener?.onItemClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_bookmark, parent, false)
        context = parent.context
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataSet[position])
    }

    override fun getItemCount() = dataSet.size

    fun setOnItemClickListener(listener: OnItemClickListener) {
        onItemClickListener = listener
    }

    interface OnItemClickListener {
        fun onItemClick(item: Item)
    }
}