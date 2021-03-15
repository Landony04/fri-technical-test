package com.example.fritechnicaltest.providers

import android.util.Log
import com.example.fritechnicaltest.api.BaseRestAdapter
import com.example.fritechnicaltest.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserProvider {
    fun getUserInfo(
        message: String,
        clientId: String,
        username: String,
        onGetUserListener: OnGetUserListener
    ) {
        val baseRestAdapter = BaseRestAdapter()
        val apiMethods = baseRestAdapter.apiMethods()

        apiMethods?.getInfoUser(username, clientId)?.enqueue(object : Callback<User> {
            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.e("Clavo", "Este es el error ${t.message}")
                onGetUserListener.getUserFailure(message)
            }

            override fun onResponse(
                call: Call<User>,
                response: Response<User>
            ) {
                if (response.isSuccessful
                    && response.code() == ImageProvider.STATUS_SUCCESS
                    && response.body() != null
                ) {
                    onGetUserListener.getUserSuccess(response.body()!!)
                } else {
                    onGetUserListener.getUserFailure(message)
                }
            }
        })
    }

    interface OnGetUserListener {
        fun getUserSuccess(user: User)
        fun getUserFailure(message: String)
    }
}