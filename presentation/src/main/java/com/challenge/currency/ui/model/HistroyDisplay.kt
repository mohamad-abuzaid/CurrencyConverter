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
  @IgnoredOnParcel val symbols = if (dates.isNotEmpty()) rates[dates[0]]?.keys?.toList()?.sorted()?: emptyList() else emptyList()
  fun getDates(i: Int) = if (dates.isNotEmpty() && i < dates.size) dates[i] else ""

  fun getRates(i: Int, curr:String) = if (dates.isNotEmpty() && i < dates.size) rates[dates[i]]?.get(curr) else ""

  fun getCompare(i: Int) = if (symbols.isNotEmpty() && i < symbols.size) "${symbols[i]}: ${rates[dates[0]]?.get(symbols[i])}" else ""
}