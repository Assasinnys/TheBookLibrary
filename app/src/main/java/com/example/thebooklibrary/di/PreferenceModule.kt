package com.example.thebooklibrary.di

import android.content.Context
import android.content.SharedPreferences
import com.example.thebooklibrary.util.BOOK_PREF
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PreferenceModule {

    @Provides
    @Singleton
    fun getPreferences(appContext: Context): SharedPreferences {
        return appContext.getSharedPreferences(BOOK_PREF, Context.MODE_PRIVATE)
    }
}