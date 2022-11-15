package com.challenge.currency.ui.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ConversionDisplay(
  val query: QueryDisplay,

  val result: Double,
) : Parcelable

@Parcelize
data class QueryDisplay(
  val amount: Double,
) : Parcelable