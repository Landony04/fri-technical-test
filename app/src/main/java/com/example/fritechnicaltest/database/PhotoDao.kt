package com.example.fritechnicaltest.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PhotoDao {
    @Query("SELECT * FROM photoDb")
    fun getAll(): LiveData<List<PhotoDb>?>?

    @Query("SELECT * FROM photoDb WHERE uid = :id")
    fun loadAllById(id: Int): LiveData<List<PhotoDb>?>?

    @Insert
    fun insertAll(vararg users: PhotoDb?)

    @Delete
    fun delete(user: PhotoDb?)
}