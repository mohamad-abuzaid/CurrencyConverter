package com.challenge.currency.ui.uistate

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CurrencyUiState(
  val isLoading: Boolean = false,
  val currencies: Set<String> = emptySet(),
  val isError: Boolean = false,
  val isApi: Boolean = false,
  val currFrom: Int = 0,
  val currTo: Int = 0,
) : Parcelable {

  sealed class FetchedState {
    object Loading : FetchedState() // for simplicity: initial loading & refreshing

    data class Fetched(val currencies: Map<String, String>) : FetchedState()

    data class Error(val throwable: Throwable) : FetchedState()
  }
}
