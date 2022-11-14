package com.challenge.currency.mappers

import com.challenge.currency.remote.models.ResponseRemote
import eu.krzdabrowski.starter.basicfeature.data.local.model.RocketCached
import eu.krzdabrowski.starter.basicfeature.data.remote.model.RocketResponse
import eu.krzdabrowski.starter.basicfeature.domain.model.Rocket
import java.time.LocalDate
import java.time.format.DateTimeFormatter

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