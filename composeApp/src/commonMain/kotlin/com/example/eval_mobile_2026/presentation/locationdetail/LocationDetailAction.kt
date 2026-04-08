package com.example.eval_mobile_2026.presentation.locationdetail

sealed interface LocationDetailAction {
    data object Retry : LocationDetailAction
}
