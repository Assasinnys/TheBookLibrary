package com.example.thebooklibrary.di

import android.content.Context
import com.example.thebooklibrary.ui.bookdetails.BookDetailsFragment
import com.example.thebooklibrary.ui.booklist.BookListFragment
import com.example.thebooklibrary.ui.login.LoginFragment
import com.example.thebooklibrary.ui.registration.RegistrationFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ViewModelModule::class, NetworkModule::class, PreferenceModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance appContext: Context): AppComponent
    }

    fun inject(fragment: LoginFragment)
    fun inject(fragment: RegistrationFragment)
    fun inject(fragment: BookListFragment)
    fun inject(fragment: BookDetailsFragment)
}