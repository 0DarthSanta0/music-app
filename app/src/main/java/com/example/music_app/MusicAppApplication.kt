package com.example.music_app

import android.app.Application
import android.content.Context

class MusicAppApplication: Application()  {
    init {
        instance = this
    }

    companion object {
        private var instance: MusicAppApplication? = null

        fun applicationContext() : Context {
            return instance!!.applicationContext
        }
    }

}