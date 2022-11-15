package com.challenge.currency.ui.uistate

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CurrencyUiState(
  val isLoading: Boolean = false,
  val currencies: Map<String, String> = emptyMap(),
  val isError: Boolean = false
) : Parcelable {

  sealed class FetchedState {
    object Loading : FetchedState() // for simplicity: initial loading & refreshing

    data class Fetched(val currencies: Map<String, String>) : FetchedState()

    data class Error(val throwable: Throwable) : FetchedState()
  }
}
