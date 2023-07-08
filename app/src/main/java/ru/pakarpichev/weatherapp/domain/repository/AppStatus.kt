package ru.pakarpichev.weatherapp.domain.repository

import android.content.Context

interface AppStatus {
    fun checkInternet (context: Context): Boolean
}