package com.example.eval_mobile_2026.di

import com.example.eval_mobile_2026.audio.SoundManager
import com.example.eval_mobile_2026.data.local.LocationCache
import com.example.eval_mobile_2026.data.remote.LocationService
import com.example.eval_mobile_2026.data.repository.LocationRepositoryImpl
import com.example.eval_mobile_2026.domain.repository.LocationRepository
import com.example.eval_mobile_2026.presentation.locationdetail.LocationDetailViewModel
import com.example.eval_mobile_2026.presentation.locationlist.LocationListViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    single { SoundManager() }
    single { LocationCache() }
    single { LocationService(get()) }
    single { LocationRepositoryImpl(get(), get()) } bind LocationRepository::class
    viewModelOf(::LocationListViewModel)
    viewModel { params -> LocationDetailViewModel(get(), params.get()) }
}
