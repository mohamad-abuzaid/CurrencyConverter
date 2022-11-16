package com.challenge.data.remote.models

import com.google.gson.annotations.SerializedName

data class HistoryRemote(
  @SerializedName("success")
  val success: Boolean,

  @SerializedName("base")
  val base: String?,

  @SerializedName("rates")
  val rates: Map<String, Map<String, String>?>?,
)