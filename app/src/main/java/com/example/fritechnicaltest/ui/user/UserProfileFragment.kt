package com.example.fritechnicaltest.ui.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fritechnicaltest.R
import com.example.fritechnicaltest.adapter.PhotoUserAdapter
import com.example.fritechnicaltest.model.User
import com.example.fritechnicaltest.providers.UserProvider
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class UserProfileFragment : Fragment(), UserProvider.OnGetUserListener {

    lateinit var photoUserImageView: CircleImageView
    lateinit var withoutPhotosImageView: ImageView
    lateinit var withoutPhotosTextView: TextView
    lateinit var progressBar: ProgressBar
    lateinit var photosCountUserTextView: TextView
    lateinit var followersCountTextView: TextView
    lateinit var followingCountUserTextView: TextView
    lateinit var nameUserTextView: TextView
    lateinit var descriptionUserTextView: TextView
    lateinit var locationUserTextView: TextView
    lateinit var photosRecyclerView: RecyclerView

    lateinit var urlPhotoUser: String
    lateinit var nameUser: String
    lateinit var location: String
    lateinit var username: String
    lateinit var bio: String

    private lateinit var userProvider: UserProvider
    lateinit var photoAdapter: PhotoUserAdapter

    companion object {
        fun newInstance() = UserProfileFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.user_profile_fragment, container, false)

        photoUserImageView = view.findViewById(R.id.photo_profile_user)
        photosCountUserTextView = view.findViewById(R.id.total_photos)
        followersCountTextView = view.findViewById(R.id.followers_count)
        followingCountUserTextView = view.findViewById(R.id.following_count)
        nameUserTextView = view.findViewById(R.id.name_user)
        descriptionUserTextView = view.findViewById(R.id.description_user)
        locationUserTextView = view.findViewById(R.id.location_user)
        photosRecyclerView = view.findViewById(R.id.photos_user)
        withoutPhotosImageView = view.findViewById(R.id.without_images_user)
        withoutPhotosTextView = view.findViewById(R.id.without_images_user_text)
        progressBar = view.findViewById(R.id.progress_circular)

        userProvider = UserProvider()
        initViews()

        if (arguments != null) {
            location = arguments?.get("location")?.toString() ?: "No tiene location"
            nameUser = arguments?.get("name")?.toString() ?: "No tiene name"
            urlPhotoUser = arguments?.get("profile_image")?.toString() ?: "Error"
            username = arguments?.get("username")?.toString() ?: "No tiene username"
            bio = arguments?.get("bio")?.toString() ?: "No tiene bio"
            setData()
            if (username != "Error") {
                getInfoUser()
            } else {
                withoutRequest()
            }
        }

        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                try {
                    Navigation.findNavController(view!!).navigate(R.id.navigation_dashboard)
                } catch (e: Exception) {
                }
            }
        }
        activity?.onBackPressedDispatcher?.addCallback(
            viewLifecycleOwner,
            onBackPressedCallback
        )

        return view
    }

    private fun initViews() {
        photoAdapter = PhotoUserAdapter(
            ArrayList(),
            ContextCompat.getDrawable(requireContext(), R.drawable.placeholder_photo)!!
        )
        photosRecyclerView.setHasFixedSize(true)
        val layoutManager = GridLayoutManager(activity, 3)
        photosRecyclerView.layoutManager = layoutManager
        photosRecyclerView.adapter = photoAdapter
        photosRecyclerView.recycledViewPool.setMaxRecycledViews(0, 0)
    }

    private fun setData() {
        Picasso.get()
            .load(urlPhotoUser)
            .into(photoUserImageView)
        nameUserTextView.text = nameUser
        locationUserTextView.text = location
        descriptionUserTextView.text = bio
    }

    private fun getInfoUser() {
        userProvider.getUserInfo(
            requireContext().getString(R.string.message_error),
            "Client-ID KzHkvOZq-MOZttVsBA2dI86GvTIMF2UERZ6fo4vIuRw",
            username,
            this
        )
    }

    private fun withoutRequest() {
        progressBar.visibility = View.GONE
        photosRecyclerView.visibility = View.GONE
        withoutPhotosImageView.visibility = View.VISIBLE
        withoutPhotosTextView.visibility = View.VISIBLE
    }

    private fun withRequest() {
        progressBar.visibility = View.GONE
        withoutPhotosImageView.visibility = View.GONE
        withoutPhotosTextView.visibility = View.GONE
        photosRecyclerView.visibility = View.VISIBLE
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun getUserSuccess(user: User) {
        if (user.photos != null && user.photos.isNotEmpty()) {
            withRequest()
            photoAdapter.addPhotos(user.photos)
        } else {
            withoutRequest()
        }
        photosCountUserTextView.text = user.totalPhotos.toString()
        followersCountTextView.text = user.followers_count.toString()
        followingCountUserTextView.text = user.following_count.toString()
    }

    override fun getUserFailure(message: String) {
        withoutRequest()
    }
}