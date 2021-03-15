package com.example.fritechnicaltest.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.NavDeepLinkBuilder
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fritechnicaltest.R
import com.example.fritechnicaltest.adapter.OptionFavoriteSelectedListener
import com.example.fritechnicaltest.adapter.PhotoFavoriteAdapter
import com.example.fritechnicaltest.database.PhotoDb

class DashboardFragment : Fragment(), OptionFavoriteSelectedListener {

    private lateinit var photoRecyclerView: RecyclerView
    private lateinit var withoutFavoritesTextView: TextView
    private lateinit var withoutFavoritesImageView: ImageView

    lateinit var photoAdapter: PhotoFavoriteAdapter
    lateinit var dashboardViewModel: DashboardViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
        photoRecyclerView = root.findViewById(R.id.recyclerView_photos_favorites)
        withoutFavoritesTextView = root.findViewById(R.id.without_favorites_textview)
        withoutFavoritesImageView = root.findViewById(R.id.without_favorites_imageView)

        dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        initViews()
        return root
    }

    private fun initViews() {
        photoAdapter = PhotoFavoriteAdapter(
            ArrayList(),
            ContextCompat.getDrawable(requireContext(), R.drawable.placeholder_photo)!!,
            requireContext(),
            this
        )
        photoRecyclerView.setHasFixedSize(true)
        val layoutManager = GridLayoutManager(activity, 1)
        photoRecyclerView.layoutManager = layoutManager
        photoRecyclerView.adapter = photoAdapter
        photoRecyclerView.recycledViewPool.setMaxRecycledViews(0, 0)
        dashboardViewModel.getBlogPosts()?.observe(viewLifecycleOwner, photoAdapter::setPhotoDb)
    }

    override fun withPhotos() {
        withoutFavoritesImageView.visibility = View.GONE
        withoutFavoritesTextView.visibility = View.GONE
        photoRecyclerView.visibility = View.VISIBLE
    }

    override fun selectedPhoto(photoDb: PhotoDb) {
        val bundle = Bundle()
        bundle.putString("urlImage", photoDb.urls.full)
        bundle.putLong("likes", photoDb.likes)
        bundle.putString("nameUser", photoDb.user.name)
        bundle.putString("urlUser", photoDb.user.profileImage.medium)
        bundle.putString("alt_description", photoDb.altDescription ?: "")
        bundle.putString("createdAt", photoDb.createdAt)
        openFragment(bundle, R.id.navigation_photo_information)
    }

    override fun userSelected(photo: PhotoDb) {
        val bundle = Bundle()
        bundle.putString("profile_image", photo.user.profileImage.medium)
        bundle.putString("name", photo.user.name)
        bundle.putString("location", photo.user.location)
        bundle.putString("username", photo.user.username)
        bundle.putString("bio", photo.user.bio)
        openFragment(bundle, R.id.navigation_user_information)
    }

    private fun openFragment(bundle: Bundle, destinationId: Int) {
        val pendingIntent = NavDeepLinkBuilder(requireContext())
            .setArguments(bundle)
            .setGraph(R.navigation.mobile_navigation)
            .setDestination(destinationId)
            .createPendingIntent()
        pendingIntent.send()
    }
}