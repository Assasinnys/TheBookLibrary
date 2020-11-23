package com.example.thebooklibrary.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.thebooklibrary.ui.bookdetails.BookDetailsViewModel
import com.example.thebooklibrary.ui.booklist.BookListViewModel
import com.example.thebooklibrary.ui.login.LoginViewModel
import com.example.thebooklibrary.ui.personalbooks.PersonalBooksViewModel
import com.example.thebooklibrary.ui.profile.ProfileViewModel
import com.example.thebooklibrary.ui.registration.RegistrationViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    fun loginViewModel(loginViewModel: LoginViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RegistrationViewModel::class)
    fun registrationViewModel(registrationViewModel: RegistrationViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(BookListViewModel::class)
    fun bookListViewModel(bookListViewModel: BookListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(BookDetailsViewModel::class)
    fun bookDetailsViewModel(bookListViewModel: BookDetailsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PersonalBooksViewModel::class)
    fun personalBooksViewModel(personalBooksViewModel: PersonalBooksViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel::class)
    fun profileViewModel(profileViewModel: ProfileViewModel): ViewModel

    @Binds
    @Singleton
    fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
