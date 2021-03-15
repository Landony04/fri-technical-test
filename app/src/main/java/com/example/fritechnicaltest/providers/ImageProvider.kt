package com.example.fritechnicaltest.providers

import com.example.fritechnicaltest.api.BaseRestAdapter
import com.example.fritechnicaltest.model.ImageModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ImageProvider {

    companion object {
        const val STATUS_SUCCESS = 200
    }

    fun getImages(
        message: String,
        clientId: String,
        page: String,
        onGetImagesListener: OnGetImagesListener
    ) {
        val baseRestAdapter = BaseRestAdapter()
        val apiMethods = baseRestAdapter.apiMethods()

        apiMethods?.getImages(page, clientId)?.enqueue(object : Callback<ArrayList<ImageModel>> {
            override fun onFailure(call: Call<ArrayList<ImageModel>>, t: Throwable) {
                onGetImagesListener.getImagesFailure(message)
            }

            override fun onResponse(
                call: Call<ArrayList<ImageModel>>,
                response: Response<ArrayList<ImageModel>>
            ) {
                if (response.isSuccessful
                    && response.code() == STATUS_SUCCESS
                    && response.body() != null
                ) {
                    onGetImagesListener.getImagesSuccess(response.body()!!)
                } else {
                    onGetImagesListener.getImagesFailure(message)
                }
            }
        })
    }

    interface OnGetImagesListener {
        fun getImagesSuccess(photoList: ArrayList<ImageModel>)
        fun getImagesFailure(message: String)
    }
}