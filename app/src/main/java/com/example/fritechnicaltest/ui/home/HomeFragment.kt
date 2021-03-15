package com.example.fritechnicaltest.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavDeepLinkBuilder
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.fritechnicaltest.R
import com.example.fritechnicaltest.adapter.OptionSelectedListener
import com.example.fritechnicaltest.adapter.PhotoAdapter
import com.example.fritechnicaltest.database.*
import com.example.fritechnicaltest.model.ImageModel
import com.example.fritechnicaltest.providers.ImageProvider
import com.example.fritechnicaltest.utils.PaginationListener
import com.example.fritechnicaltest.utils.PaginationListener.Companion.PAGE_START

class HomeFragment : Fragment(), PhotosView, OptionSelectedListener,
    SwipeRefreshLayout.OnRefreshListener {

    lateinit var imageProvider: ImageProvider
    lateinit var photoRecyclerView: RecyclerView
    lateinit var swipeRefreshLayout: SwipeRefreshLayout

    lateinit var photosPresenter: PhotosPresenter
    lateinit var photoAdapter: PhotoAdapter

    private var page: Int = PAGE_START

    private var isLastPage = false
    private var totalPage = 10
    private var isLoading = false
    private var itemCount = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        photoRecyclerView = root.findViewById(R.id.recyclerView_photos)
        swipeRefreshLayout = root.findViewById(R.id.swipe_refresh)
        swipeRefreshLayout.setOnRefreshListener(this)
        imageProvider = ImageProvider()
        photosPresenter = PhotosPresenterImpl(this, this.requireContext())
        initViews()
        getPhotos()

        return root
    }

    override fun getPhotos() {
        photosPresenter.getPhotos(page.toString())
    }

    override fun initViews() {
        photoAdapter = PhotoAdapter(
            ArrayList(),
            ContextCompat.getDrawable(requireContext(), R.drawable.placeholder_photo)!!, this
        )
        photoRecyclerView.setHasFixedSize(true)
        val layoutManager = GridLayoutManager(activity, 1)
        photoRecyclerView.layoutManager = layoutManager
        photoRecyclerView.adapter = photoAdapter
        photoRecyclerView.recycledViewPool.setMaxRecycledViews(0, 0)
        photoRecyclerView.addOnScrollListener(object : PaginationListener(layoutManager) {
            override fun loadMoreItems() {
                isLoading = true
                page++
                getPhotos()
            }

            override fun isLastPage(): Boolean {
                return isLastPage
            }

            override fun isLoading(): Boolean {
                return isLoading
            }
        })
    }

    override fun fillPhotos(photoList: ArrayList<ImageModel>) {
        if (photoList.isNotEmpty()) {
            if (page != PAGE_START) photoAdapter.removeLoading()
            photoAdapter.addPhotos(photoList)
            swipeRefreshLayout.isRefreshing = false
            if (page < totalPage) {
                photoAdapter.addLoading()
            } else {
                isLastPage = true
            }
            isLoading = false
        }
    }

    override fun onRefresh() {
        itemCount = 0
        page = PAGE_START
        isLastPage = false
        photoAdapter.clear()
        getPhotos()
    }

    override fun addFavorite(photoSelected: ImageModel) {
        val urlData = UrlData(photoSelected.urls?.full ?: "")
        val profileImageData = ProfileImageData(photoSelected.user?.profile_image?.medium ?: "")
        val userData =
            UserData(
                photoSelected.user?.id ?: "",
                photoSelected.user?.updatedAt ?: "",
                photoSelected.user?.username ?: "",
                photoSelected.user?.name ?: "",
                photoSelected.user?.firstName ?: "",
                photoSelected.user?.lastName ?: "",
                profileImageData,
                photoSelected.user?.twitterUsername ?: "",
                photoSelected.user?.portfolioURL ?: "",
                photoSelected.user?.bio ?: "",
                photoSelected.user?.location ?: "",
                photoSelected.user?.instagramUsername ?: "",
                photoSelected.user?.totalCollections!!,
                photoSelected.user?.totalLikes!!,
                photoSelected.user?.totalPhotos!!,
                photoSelected.user?.acceptedTos!!,
                photoSelected.user?.forHire ?: false,
                photoSelected.user?.followers_count ?: 0,
                photoSelected.user?.following_count ?: 0,
            )
        val photoDb = PhotoDb(
            0,
            photoSelected.id,
            photoSelected.description ?: "",
            photoSelected.alt_description ?: "",
            photoSelected.created_at,
            urlData,
            photoSelected.likes,
            userData
        )
        Thread {
            AppDatabase.getDatabase(requireContext()).photoDao().insertAll(photoDb)
        }.start()
    }

    override fun optionSelected(photo: ImageModel) {
        val bundle = Bundle()
        bundle.putString("urlImage", photo.urls?.full ?: "")
        bundle.putLong("likes", photo.likes)
        bundle.putString("nameUser", photo.user?.name ?: "")
        bundle.putString("urlUser", photo.user?.profile_image?.medium ?: "")
        bundle.putString("alt_description", photo.alt_description ?: "")
        bundle.putString("createdAt", photo.created_at)
        openFragment(bundle, R.id.navigation_photo_information)
    }

    override fun userSelected(photo: ImageModel) {
        val bundle = Bundle()
        bundle.putString("profile_image", photo.user?.profile_image?.medium)
        bundle.putString("name", photo.user?.name)
        bundle.putString("location", photo.user?.location)
        bundle.putString("username", photo.user?.username)
        bundle.putString("bio", photo.user?.bio)
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