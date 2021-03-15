package com.example.fritechnicaltest.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.fritechnicaltest.R
import com.example.fritechnicaltest.model.PhotosUser
import com.squareup.picasso.Picasso

class PhotoUserAdapter(
    private var photoArrayList: ArrayList<PhotosUser>,
    private var placeholder: Drawable
) :
    RecyclerView.Adapter<PhotoUserAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_photo_user, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return photoArrayList.size
    }

    fun addPhotos(photosList: ArrayList<PhotosUser>) {
        this.photoArrayList.addAll(photosList)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val photo = photoArrayList[position]

        Picasso.get().load(photo.urls?.full)
            .placeholder(placeholder)
            .resize(1900, 800)
            .into(holder.userPhotoImageView)
    }

    override fun getItemViewType(position: Int): Int {
        return 1
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userPhotoImageView = itemView.findViewById(R.id.photo_user) as ImageView
    }
}