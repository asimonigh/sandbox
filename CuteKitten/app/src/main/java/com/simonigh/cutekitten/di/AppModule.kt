package com.simonigh.cutekitten.di

import com.simonigh.cutekitten.data.CatRepository
import com.simonigh.cutekitten.data.remote.CatsApi
import com.simonigh.cutekitten.data.remote.CatsApi.Companion
import com.simonigh.cutekitten.presentation.CatListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { CatRepository(CatsApi.create()) }
    viewModel { CatListViewModel(get()) }
}