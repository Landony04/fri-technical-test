package com.example.fritechnicaltest.database

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PhotoDb(
    @PrimaryKey(autoGenerate = true)
    var uid: Int,
    @ColumnInfo(name = "photo_id")
    var id: String,
    @ColumnInfo(name = "description")
    var description: String,
    @ColumnInfo(name = "alt_description")
    var altDescription: String,
    @ColumnInfo(name = "created_at")
    var createdAt: String,
    @Embedded
    var urls: UrlData,
    @ColumnInfo(name = "likes")
    var likes: Long,
    @Embedded
    var user: UserData
)

data class UrlData(@ColumnInfo(name = "full") var full: String)

data class UserData(
    val id: String,
    val updatedAt: String,
    val username: String,
    val name: String,
    val firstName: String,
    val lastName: String,
    @Embedded
    val profileImage: ProfileImageData,
    val twitterUsername: String,
    val portfolioURL: String? = null,
    val bio: String? = null,
    val location: String? = null,
    val instagramUsername: String? = null,
    val totalCollections: Long,
    val totalLikes: Long,
    val totalPhotos: Long,
    val acceptedTos: Boolean,
    val forHire: Boolean? = null,
    val followersCount: Int,
    val followingCount: Int
)

data class ProfileImageData(
    val medium: String
)
