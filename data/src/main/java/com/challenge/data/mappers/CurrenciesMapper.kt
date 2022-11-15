package com.challenge.data.mappers

import com.challenge.data.remote.models.CurrenciesRemote
import com.challenge.domain.model.CurrenciesModel

fun CurrenciesRemote.toCurrenciesModel() = CurrenciesModel(
  symbols = symbols ?: emptyMap(),
)