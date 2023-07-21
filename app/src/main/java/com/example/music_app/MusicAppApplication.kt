package com.example.music_app

import android.app.Application
import android.content.Context

class MusicAppApplication: Application()  {

    override fun onCreate() {
        instance = this
        super.onCreate()
    }

    companion object {
        private var instance: MusicAppApplication? = null

        fun applicationContext() : Context? {
            return instance?.applicationContext
        }
    }

}