package com.example.music_app.constants

const val SPOTIFY_CLIENT_ID = "67f6367abcd3497db54043591d9e7c9f"
const val SPOTIFY_CLIENT_SECRET = "6a0137ad789a404db79c6e4d6625bf49"

//const val SPOTIFY_CLIENT_ID = "b18ebe5c936a47c1a6c2ed206bb5f57c"
//const val SPOTIFY_CLIENT_SECRET = "9aca1df6f7424e82b0dadf73286cc87c"

const val CODE = "code"
const val SCOPE = "user-read-private user-read-email"
const val REDIRECT_URL = "http://localhost:3000"
const val GRANT_TYPE = "authorization_code"

const val SCOPE_QUERY_PARAM = "scope"
const val REDIRECT_QUERY_PARAM = "redirect_uri"
const val TYPE_QUERY_PARAM = "response_type"
const val ID_QUERY_PARAM = "client_id"

const val BASE_URL = "https://accounts.spotify.com/"
const val AUTH_URL = "${BASE_URL}authorize?${ID_QUERY_PARAM}=$SPOTIFY_CLIENT_ID&$TYPE_QUERY_PARAM=$CODE&$SCOPE_QUERY_PARAM=$SCOPE&$REDIRECT_QUERY_PARAM=$REDIRECT_URL"