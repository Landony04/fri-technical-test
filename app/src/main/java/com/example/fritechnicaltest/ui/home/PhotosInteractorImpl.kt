package com.example.fritechnicaltest.ui.home
import com.example.fritechnicaltest.providers.ImageProvider

class PhotosInteractorImpl : PhotosInteractor {
    override fun getPhotos(
        message: String,
        page: String,
        clientId: String,
        onGetImagesListener: ImageProvider.OnGetImagesListener
    ) {
        val imageProvider = ImageProvider()
        imageProvider.getImages(message, clientId, page, onGetImagesListener)
    }
}