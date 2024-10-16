package com.behruzbek0430.firebasegarasmlaryuklashvakorish

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide

class ViewPagerActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2
    private lateinit var imageUrls: List<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_pager)

        imageUrls = intent.getStringArrayListExtra("imageUrls") ?: listOf()

        viewPager = findViewById(R.id.viewPager)
        viewPager.adapter = ImageAdapter(imageUrls)
    }
}

class ImageAdapter(private val imageUrls: List<String>) : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {
    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(url: String) {
            Glide.with(itemView.context).load(url).into(itemView.findViewById<ImageView>(R.id.imageView))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(imageUrls[position])
    }

    override fun getItemCount(): Int = imageUrls.size

}