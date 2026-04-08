package com.example.eval_mobile_2026.presentation.locationdetail

/**
 * User intentions on the location detail screen.
 *
 * Each action is dispatched to [LocationDetailViewModel.onAction] and triggers
 * a well-defined state transition.
 */
sealed interface LocationDetailAction {
    /** Retry the last failed location fetch. */
    data object Retry : LocationDetailAction
}
