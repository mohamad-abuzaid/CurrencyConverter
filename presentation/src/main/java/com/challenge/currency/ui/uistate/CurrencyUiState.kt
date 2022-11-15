package com.challenge.currency.ui.uistate

import android.os.Parcelable
import com.challenge.currency.ui.model.ConversionDisplay
import kotlinx.parcelize.Parcelize

@Parcelize
data class CurrencyUiState(
  val isLoading: Boolean = false,
  val currencies: Set<String> = emptySet(),
  val isError: Boolean = false,
  val isApi: Boolean = false,
  val currFromPos: Int = 0,
  val currFromVal: Double = 0.0,
  val currToPos: Int = 0,
  val currToVal: Double = 0.0,
) : Parcelable {

  sealed class FetchedState {
    object Loading : FetchedState() // for simplicity: initial loading & refreshing

    data class Fetched(val currencies: Map<String, String>) : FetchedState()

    data class Converted(val conversion: ConversionDisplay) : FetchedState()

    data class Error(val throwable: Throwable) : FetchedState()
  }
}
