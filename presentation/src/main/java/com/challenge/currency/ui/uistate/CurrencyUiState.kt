package com.challenge.currency.ui.uistate

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CurrencyUiState(
  val isLoading: Boolean = false,
  val currencies: Map<String, String> = emptyMap(),
  val isError: Boolean = false
) : Parcelable {

  sealed class FetchState {
    object Loading : FetchState() // for simplicity: initial loading & refreshing

    data class Fetched(val currencies: Map<String, String>) : FetchState()

    data class Error(val throwable: Throwable) : FetchState()
  }
}
