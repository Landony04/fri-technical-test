package com.example.fritechnicaltest.ui.home

import com.example.fritechnicaltest.model.ImageModel

interface PhotosView {
    fun getPhotos()

    fun initViews()

    fun fillPhotos(photoList: ArrayList<ImageModel>)
}