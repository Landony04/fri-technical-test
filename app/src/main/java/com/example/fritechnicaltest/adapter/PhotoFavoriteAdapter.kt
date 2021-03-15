package com.example.fritechnicaltest.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.fritechnicaltest.R
import com.example.fritechnicaltest.database.AppDatabase
import com.example.fritechnicaltest.database.PhotoDb
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class PhotoFavoriteAdapter(
    private var photoListDb: List<PhotoDb>,
    private var placeholder: Drawable,
    private var context: Context,
    private var optionSelectedListener: OptionFavoriteSelectedListener
) :
    RecyclerView.Adapter<PhotoFavoriteAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_photos, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("WrongConstant")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val photo = photoListDb[position]

        Picasso.get().load(photo.urls.full)
            .placeholder(placeholder)
            .resize(1900, 1400)
            .into(holder.photoImageView)

        Picasso.get()
            .load(photo.user.profileImage.medium)
            .placeholder(placeholder)
            .into(holder.userPhotoImageView)

        holder.favoriteImageButton.setImageResource(R.drawable.ic_baseline_favorite_24)
        holder.nameTextView.text = photo.user.name
        holder.likeTextView.text = photo.likes.toString()
        if (photo.description.isNotEmpty()) {
            holder.layoutDescriptionContainer.visibility = View.VISIBLE
            holder.descriptionTextView.text =
                HtmlCompat.fromHtml(
                    "<b>${photo.user.username}</b> ${photo.description}",
                    Typeface.BOLD
                )
        }
        holder.favoriteImageButton.setOnClickListener {
            removePhoto(photo)
        }

        holder.layoutContainer.setOnClickListener {
            optionSelectedListener.selectedPhoto(photo)
        }

        holder.userContainer.setOnClickListener {
            optionSelectedListener.userSelected(photo)
        }
    }

    override fun getItemCount(): Int {
        return photoListDb.size
    }

    fun setPhotoDb(photos: List<PhotoDb>?) {
        if (photos?.size ?: 0 > 0) {
            optionSelectedListener.withPhotos()
        }
        this.photoListDb = photos!!
        notifyDataSetChanged()
    }

    private fun removePhoto(photo: PhotoDb) {
        val position = photoListDb.indexOf(photo)
        if (position > -1) {
            Thread {
                AppDatabase.getDatabase(context).photoDao().delete(photo)
                photoListDb.toMutableList().remove(photo)
                notifyItemRemoved(position)
            }.start()
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userPhotoImageView = itemView.findViewById(R.id.user_imageView) as CircleImageView
        val nameTextView = itemView.findViewById(R.id.user_textview) as TextView
        val photoImageView = itemView.findViewById(R.id.photo_imageView) as ImageView
        val likeTextView = itemView.findViewById(R.id.likes_textview) as TextView
        val descriptionTextView = itemView.findViewById(R.id.description_textview) as TextView
        val favoriteImageButton =
            itemView.findViewById(R.id.favorite_photo_imagebutton) as ImageButton
        val layoutContainer =
            itemView.findViewById(R.id.item_main_layout) as ConstraintLayout
        val layoutDescriptionContainer =
            itemView.findViewById(R.id.container_description_layout) as LinearLayout
        val userContainer = itemView.findViewById(R.id.user_container_item) as LinearLayout
    }
}

