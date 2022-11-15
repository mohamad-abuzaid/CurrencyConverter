package com.challenge.data.remote.models

import com.google.gson.annotations.SerializedName

data class ConversionRemote(
  @SerializedName("success")
  val success: Boolean,

  @SerializedName("query")
  val query: QueryRemote?,

  @SerializedName("result")
  val result: Double?,
)

data class QueryRemote(
  @SerializedName("amount")
  val amount: Double?,
)
