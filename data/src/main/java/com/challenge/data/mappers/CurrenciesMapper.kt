package com.challenge.data.mappers

import com.challenge.domain.model.CurrenciesModel
import com.challenge.data.remote.models.CurrenciesRemote

fun CurrenciesRemote.toCurrenciesModel() = CurrenciesModel(
  symbols = symbols,
)