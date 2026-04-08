package com.example.eval_mobile_2026.presentation.locationlist

sealed interface LocationListAction {
    data object LoadNextPage : LocationListAction
    data object Retry : LocationListAction
    data class SelectLocation(val id: Int) : LocationListAction
}
