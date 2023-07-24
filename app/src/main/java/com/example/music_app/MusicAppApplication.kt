package com.example.music_app

import android.app.Application
import android.content.Context

class MusicAppApplication: Application()  {

    override fun onCreate() {
        instance = this
        super.onCreate()
    }

    companion object {
        private lateinit var instance: MusicAppApplication

        fun applicationContext() : Context = instance.applicationContext
    }

}