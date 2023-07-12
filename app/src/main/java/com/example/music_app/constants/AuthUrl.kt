package com.example.music_app.constants

const val SPOTIFY_CLIENT_ID = "67f6367abcd3497db54043591d9e7c9f"
const val CODE = "code"
const val SCOPE = "user-read-private user-read-email"
const val REDIRECT_URL = "http://localhost:3000"

const val BASE_URL = "https://accounts.spotify.com"
const val AUTH_URL = "$BASE_URL/authorize?client_id=$SPOTIFY_CLIENT_ID&response_type=$CODE&scope=$SCOPE&redirect_uri=$REDIRECT_URL"