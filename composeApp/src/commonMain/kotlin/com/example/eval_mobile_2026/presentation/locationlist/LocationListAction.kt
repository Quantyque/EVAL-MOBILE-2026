package com.example.eval_mobile_2026.presentation.locationlist

/**
 * User intentions on the location list screen.
 *
 * Each action is dispatched to [LocationListViewModel.onAction] and triggers
 * a well-defined state transition.
 */
sealed interface LocationListAction {
    /** Request the next page of results (ignored if already loading or no more pages). */
    data object LoadNextPage : LocationListAction
    /** Retry the last failed request. */
    data object Retry : LocationListAction
    /**
     * The user tapped a location card.
     * This action is not handled by the ViewModel; navigation is delegated to the
     * composable caller via the [onLocationClick] callback.
     */
    data class SelectLocation(val id: Int) : LocationListAction
}
