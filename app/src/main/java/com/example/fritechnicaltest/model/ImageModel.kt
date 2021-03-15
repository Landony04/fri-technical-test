package com.example.fritechnicaltest.model

class ImageModel {
    var id: String = ""
    var urls: Urls? = null
    var likes: Long = 0L
    var user: User? = null
    var description: String? = null
    var alt_description: String? = null
    var created_at: String = ""
    var width: Int = 1000
    var height: Int = 1000
}

class User {
    var id: String = ""
    var name: String = ""
    var profile_image: ProfileImage? = null
    var username: String = ""
    val updatedAt: String = ""
    val firstName: String = ""
    val lastName: String = ""
    val twitterUsername: String = ""
    val portfolioURL: String? = null
    val bio: String? = null
    val location: String? = null
    val instagramUsername: String? = null
    val totalCollections: Long = 0L
    val totalLikes: Long = 0L
    val totalPhotos: Long = 0L
    val acceptedTos: Boolean = false
    val forHire: Boolean? = null
    val photos: ArrayList<PhotosUser>? = null
    val followers_count: Int = 0
    val following_count: Int = 0
}

class ProfileImage {
    var medium: String = ""
}

class PhotosUser {
    var urls: Urls? = null
}

class Urls {
    var full: String = ""
}

