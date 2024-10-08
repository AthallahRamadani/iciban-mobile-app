package com.example.iciban.di

import com.example.iciban.fragment.home.HomeViewModel
import com.example.iciban.fragment.prelogin.login.LoginViewModel
import com.example.iciban.fragment.prelogin.register.RegisterViewModel
import com.example.iciban.fragment.select.SelectBannerViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val vmModule = module {
    viewModel { RegisterViewModel(get(), get()) }
    viewModel { HomeViewModel(get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { SelectBannerViewModel()}
}