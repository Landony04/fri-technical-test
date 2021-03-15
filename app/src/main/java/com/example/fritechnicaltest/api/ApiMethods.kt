package com.example.fritechnicaltest.api

import com.example.fritechnicaltest.model.ImageModel
import com.example.fritechnicaltest.model.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiMethods {
    @GET("photos")
    fun getImages(
        @Query("page") page: String,
        @Header("Authorization") clientId: String
    ): Call<ArrayList<ImageModel>>

    @GET("users/{user}")
    fun getInfoUser(
        @Path("user") username: String,
        @Header("Authorization") clientId: String
    ): Call<User>
}