package com.challenge.currency.remote.models

import com.google.gson.annotations.SerializedName

data class ResponseRemote(
    @SerializedName("id")
    val id: String = "",

    @SerializedName("name")
    val name: String = "",

    @SerializedName("cost_per_launch")
    val costPerLaunch: Int = 0,

    @SerializedName("first_flight")
    val firstFlightDate: String = ""
)
