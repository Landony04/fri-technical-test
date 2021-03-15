package com.example.fritechnicaltest.ui.dashboard

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.fritechnicaltest.database.AppDatabase
import com.example.fritechnicaltest.database.PhotoDb

class DashboardViewModel(application: Application) :
    AndroidViewModel(application) {

    private var photos: LiveData<List<PhotoDb>?>? = null

    init {
        photos = AppDatabase.getDatabase(application).photoDao().getAll()
    }

    fun getBlogPosts(): LiveData<List<PhotoDb>?>? {
        return photos
    }
}