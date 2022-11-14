package com.challenge.currency.mappers

import com.challenge.currency.remote.models.ResponseRemote
import com.challenge.currency.model.Rocket
import java.time.LocalDate

fun ResponseRemote.toResponseModel() = Rocket(
    id = id,
    name = name,
    costPerLaunch = costPerLaunch,
    firstFlight = LocalDate.parse(firstFlightDate),
    height = height.meters.toInt(),
    weight = weight.kg,
    wikiUrl = wikiUrl,
    imageUrl = imageUrls.random()
)