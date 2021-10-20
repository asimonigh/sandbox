package com.simonigh.cutekitten.di

import com.simonigh.cutekitten.data.CatRepository
import com.simonigh.cutekitten.data.local.CacheDataBase
import com.simonigh.cutekitten.data.remote.CatsApi
import com.simonigh.cutekitten.presentation.CatListViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { CacheDataBase.build(androidApplication()).catDao }
    single { CatRepository(CatsApi.create(), get()) }
    viewModel { CatListViewModel(get()) }
}