package com.example.fritechnicaltest.ui.home
import com.example.fritechnicaltest.providers.ImageProvider

interface PhotosInteractor {
    fun getPhotos(
        message: String,
        page: String,
        clientId: String,
        onGetImagesListener: ImageProvider.OnGetImagesListener
    )
}