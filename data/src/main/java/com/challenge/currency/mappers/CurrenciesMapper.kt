package com.challenge.currency.mappers

import com.challenge.currency.model.CurrenciesModel
import com.challenge.currency.remote.models.CurrenciesRemote

fun CurrenciesRemote.toCurrenciesModel() = CurrenciesModel(
  symbols = symbols,
)