package com.example.music_app.data.models

import com.google.gson.annotations.SerializedName

data class PlaylistsResponse(
    val href: String?,
    val limit: Int?,
    val next: String?,
    val offset: Int?,
    val previous: String?,
    val total: Int?,
    val items: List<PlaylistItem>?
)

data class PlaylistItem(
    val collaborative: Boolean,
    val description: String,
    @SerializedName("external_urls")
    val externalUrls: ExternalUrls,
    val href: String,
    val id: String,
    val images: List<Image>,
    val name: String,
    val owner: Owner,
    val public: Boolean,
    @SerializedName("snapshot_id")
    val snapshotId: String,
    val tracks: Tracks,
    val type: String,
    val uri: String
)

data class ExternalUrls(
    val spotify: String
)

data class Image(
    val url: String,
    val height: Int,
    val width: Int
)

data class Owner(
    @SerializedName("external_urls")
    val externalUrls: ExternalUrls,
    val followers: Followers,
    val href: String,
    val id: String,
    val type: String,
    val uri: String,
    @SerializedName("display_name")
    val displayName: String
)

data class Followers(
    val href: String,
    val total: Int
)

data class Tracks(
    val href: String,
    val total: Int
)