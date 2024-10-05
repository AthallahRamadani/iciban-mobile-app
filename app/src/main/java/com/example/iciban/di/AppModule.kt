package com.example.iciban.di

import com.example.iciban.fragment.register.RegisterViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val vmModule = module {
    viewModel { RegisterViewModel(get()) }
}