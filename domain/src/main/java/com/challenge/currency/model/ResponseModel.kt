package com.challenge.currency.model

import java.time.LocalDate

data class ResponseModel(
    val id: String,
    val name: String,
    val costPerLaunch: Int,
    val firstFlight: LocalDate,
    val height: Int,
    val weight: Int,
    val wikiUrl: String,
    val imageUrl: String
)
