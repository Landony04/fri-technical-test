package com.example.fritechnicaltest.adapter

import android.annotation.SuppressLint
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
import com.example.fritechnicaltest.model.ImageModel
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class PhotoAdapter(
    private var photoList: ArrayList<ImageModel>,
    private var placeholder: Drawable,
    private var optionSelectedListener: OptionSelectedListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val VIEW_TYPE_LOADING = 0
    private val VIEW_TYPE_NORMAL = 1
    private var isLoaderVisible = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_NORMAL -> ViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_photos, parent, false)
            )
            else -> NativeExpressAdViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.loading, parent, false)
            )
        }
    }

    override fun getItemCount(): Int {
        return photoList.size
    }

    @SuppressLint("SetTextI18n", "WrongConstant")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {


        val photo = photoList[position]

        when (holder.itemViewType) {
            1 -> {
                val newHolder: ViewHolder = holder as ViewHolder

                Picasso.get().load(photo.urls?.full ?: "https://i.imgur.com/tGbaZCY.jpg")
                    .placeholder(placeholder)
                    .resize(1900, 1400)
                    .into(newHolder.photoImageView)

                Picasso.get()
                    .load(photo.user?.profile_image?.medium ?: "https://i.imgur.com/tGbaZCY.jpg")
                    .placeholder(placeholder)
                    .into(newHolder.userPhotoImageView)

                newHolder.nameTextView.text = photo.user?.username ?: "Error al cargar"
                newHolder.likeTextView.text = photo.likes.toString()
                if (photo.description != null && photo.description!!.isNotEmpty()) {
                    newHolder.layoutDescriptionContainer.visibility = View.VISIBLE
                    newHolder.descriptionTextView.text =
                        HtmlCompat.fromHtml(
                            "<b>${photo.user?.username ?: ""}</b> ${photo.description}",
                            Typeface.BOLD
                        )
                }

                newHolder.favoriteImageButton.setOnClickListener {
                    optionSelectedListener.addFavorite(photo)
                }

                newHolder.layoutContainer.setOnClickListener {
                    optionSelectedListener.optionSelected(photo)
                }

                newHolder.userContainer.setOnClickListener {
                    optionSelectedListener.userSelected(photo)
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (isLoaderVisible) {
            if (position == photoList.size - 1) VIEW_TYPE_LOADING else VIEW_TYPE_NORMAL
        } else {
            VIEW_TYPE_NORMAL
        }
    }

    fun addPhotos(photoList: ArrayList<ImageModel>) {
        this.photoList.addAll(photoList)
        notifyDataSetChanged()
    }

    fun addLoading() {
        isLoaderVisible = true
        photoList.add(ImageModel())
        notifyItemInserted(this.photoList.size - 1)
    }

    fun removeLoading() {
        isLoaderVisible = false
        val position: Int = photoList.size - 1
        val item: ImageModel = getItem(position)
        photoList.remove(item)
        notifyItemRemoved(position)
    }

    fun clear() {
        photoList.clear()
        notifyDataSetChanged()
    }

    private fun getItem(position: Int): ImageModel {
        return photoList[position]
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

    class NativeExpressAdViewHolder(view: View?) :
        RecyclerView.ViewHolder(view!!)
}