package com.challenge.currency.remote.models

import com.google.gson.annotations.SerializedName

data class CurrenciesRemote(
  @SerializedName("success")
  val success: Boolean,

  @SerializedName("symbols")
  val symbols: Map<String, String>,
)
