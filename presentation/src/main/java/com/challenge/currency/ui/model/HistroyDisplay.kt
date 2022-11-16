package com.challenge.currency.ui.model

import android.os.Parcelable
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
data class HistoryDisplay(
  val base: String,
  val rates: Map<String, Map<String, String>?>,
) : Parcelable {
  @IgnoredOnParcel val dates = rates.keys.toList().sorted()
  fun getDates(i: Int) = if (dates.isNotEmpty() && i < dates.size) dates[i] else ""

}