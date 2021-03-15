package com.example.fritechnicaltest.ui.home

import com.example.fritechnicaltest.model.ImageModel
import android.content.Context
import android.widget.Toast
import com.example.fritechnicaltest.R
import com.example.fritechnicaltest.providers.ImageProvider

class PhotosPresenterImpl(private var photosView: PhotosView, private var context: Context) :
    PhotosPresenter,
    ImageProvider.OnGetImagesListener {

    private var photosInteractor = PhotosInteractorImpl()

    override fun getPhotos(page: String) {
        photosInteractor.getPhotos(
            context.getString(R.string.message_error),
            page,
            "Client-ID KzHkvOZq-MOZttVsBA2dI86GvTIMF2UERZ6fo4vIuRw",
            this
        )
    }

    override fun getImagesSuccess(photoList: ArrayList<ImageModel>) {
        if (photoList.isNotEmpty()) {
            photosView.fillPhotos(photoList)
        } else {

        }
    }

    override fun getImagesFailure(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}