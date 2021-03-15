package com.example.fritechnicaltest.adapter

import com.example.fritechnicaltest.database.PhotoDb

interface OptionFavoriteSelectedListener {
    fun withPhotos()

    fun selectedPhoto(photoDb: PhotoDb)

    fun userSelected(photo: PhotoDb)
}