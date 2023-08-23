package com.example.music_app.ui.navigation

const val LOGIN_SCREEN = "login_screen"
const val PLAYLISTS_SCREEN = "playlists_screen"
const val SEARCH_SCREEN = "search_screen"

sealed class Screens(val route: String) {
    object LoginScreen: Screens(LOGIN_SCREEN)
    object PlaylistsScreen: Screens(PLAYLISTS_SCREEN)
    object SearchScreen: Screens(SEARCH_SCREEN)
}
