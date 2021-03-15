package com.example.fritechnicaltest.ui.photo

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.fritechnicaltest.R
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class PhotoInformationFragment : Fragment() {

    lateinit var userPhotoImageView: CircleImageView
    lateinit var usernameTextView: TextView
    lateinit var photoImageView: ImageView
    lateinit var likeTextView: TextView
    lateinit var descriptionTextView: TextView
    lateinit var createdAtTextView: TextView
    lateinit var favoriteImageButton: ImageButton

    lateinit var urlImage: String
    var likes: Long = 0L
    lateinit var nameUser: String
    lateinit var urlPhotoUser: String
    lateinit var altDescription: String
    lateinit var createdAt: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.photo_information_fragment, container, false)
        if (arguments != null) {
            urlImage = arguments?.get("urlImage")?.toString() ?: "No hay nada"
            likes = arguments?.getLong("likes") ?: 0
            nameUser = arguments?.get("nameUser")?.toString() ?: "Error"
            urlPhotoUser = arguments?.get("urlUser")?.toString() ?: "Error"
            altDescription = arguments?.get("alt_description")?.toString() ?: "Error"
            createdAt = arguments?.get("createdAt")?.toString() ?: "Error"
        }

        userPhotoImageView = root.findViewById(R.id.user_imageView_information)
        usernameTextView = root.findViewById(R.id.user_textview_information)
        photoImageView = root.findViewById(R.id.photo_imageView_information)
        likeTextView = root.findViewById(R.id.likes_textview_information)
        favoriteImageButton =
            root.findViewById(R.id.favorite_photo_imagebutton_information)
        descriptionTextView =
            root.findViewById(R.id.description_textview_information)
        createdAtTextView =
            root.findViewById(R.id.created_textview_information)

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
        setData()
        return root
    }

    private fun setData() {

        Picasso.get().load(urlImage)
            .resize(1900, 1400)
            .placeholder(R.drawable.placeholder_photo)
            .into(photoImageView)

        Picasso.get()
            .load(urlPhotoUser)
            .placeholder(R.drawable.placeholder_photo)
            .into(userPhotoImageView)

        usernameTextView.text = nameUser
        likeTextView.text = likes.toString()
        if (altDescription.isNotEmpty()) {
            descriptionTextView.visibility = View.VISIBLE
            descriptionTextView.text = altDescription
        }
        createdAtTextView.text = getTimeAgo(createdAt)
    }

    @SuppressLint("SimpleDateFormat")
    private fun getTimeAgo(datePhoto: String): String {
        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
        var date: Date? = null
        try {
            date = format.parse(datePhoto)
            println(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        if (date != null) {
            val calendar = Calendar.getInstance()
            calendar.time = date

            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)

            val currentCalendar = Calendar.getInstance()

            val currentYear = currentCalendar.get(Calendar.YEAR)
            val currentMonth = currentCalendar.get(Calendar.MONTH)
            val currentDay = currentCalendar.get(Calendar.DAY_OF_MONTH)
            val currentHour = currentCalendar.get(Calendar.HOUR_OF_DAY)
            val currentMinute = currentCalendar.get(Calendar.MINUTE)

            return if (year < currentYear) {
                val interval = currentYear - year
                if (interval == 1) "Hace $interval años" else "Hace $interval años"
            } else if (month < currentMonth) {
                val interval = currentMonth - month
                if (interval == 1) "Hace $interval mes" else "Hace $interval meses"
            } else if (day < currentDay) {
                val interval = currentDay - day
                if (interval == 1) "Hace $interval día" else "Hace $interval días"
            } else if (hour < currentHour) {
                val interval = currentHour - hour
                if (interval == 1) "Hace $interval hora" else "Hace $interval horas"
            } else if (minute < currentMinute) {
                val interval = currentMinute - minute
                if (interval == 1) "Hace $interval minuto" else "Hace $interval minutos"
            } else {
                "Hace un momento"
            }
        }

        return ""
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }
}