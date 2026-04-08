package com.example.eval_mobile_2026.di

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

/**
 * Shared Koin module for the common source set.
 *
 * Provides all application-level singletons and ViewModels. This module depends on
 * [platformModule] (declared per platform) to resolve the [io.ktor.client.HttpClient]
 * singleton, which requires a platform-specific engine (Android or Java).
 *
 * - [LocationRepositoryImpl] is bound to the [LocationRepository] interface so callers
 *   in the domain and presentation layers remain implementation-agnostic.
 * - [LocationDetailViewModel] uses parameterized injection (`params.get()`) to receive
 *   the `locationId` at creation time from the call site.
 */
val appModule = module {
    // SoundManager is provided by platformModule (constructor differs per platform)
    single { LocationCache() }
    single { LocationService(get()) }
    single { LocationRepositoryImpl(get(), get()) } bind LocationRepository::class
    viewModelOf(::LocationListViewModel)
    viewModel { params -> LocationDetailViewModel(get(), params.get()) }
}
