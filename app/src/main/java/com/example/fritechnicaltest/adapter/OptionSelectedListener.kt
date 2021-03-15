package com.example.fritechnicaltest.adapter

import com.example.fritechnicaltest.model.ImageModel

interface OptionSelectedListener {
    fun addFavorite(photoSelected: ImageModel)

    fun optionSelected(photo: ImageModel)

    fun userSelected(photo: ImageModel)
}