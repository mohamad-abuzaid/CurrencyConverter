package com.challenge.currency.ui.model

import com.google.gson.annotations.SerializedName

data class HistoryDisplay(
  val success: Boolean,

  val base: String,

  val rates: Map<String, Map<String, String>>,
)