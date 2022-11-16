package com.challenge.data.mappers

import com.challenge.data.remote.models.ConversionRemote
import com.challenge.data.remote.models.CurrenciesRemote
import com.challenge.data.remote.models.HistoryRemote
import com.challenge.data.remote.models.QueryRemote
import com.challenge.domain.model.ConversionModel
import com.challenge.domain.model.CurrenciesModel
import com.challenge.domain.model.HistoryModel
import com.challenge.domain.model.QueryModel

fun CurrenciesRemote.toCurrenciesModel() = CurrenciesModel(
  symbols = symbols ?: emptyMap(),
)

fun ConversionRemote.toConversionModel() = ConversionModel(
  result = result ?: 0.0,
  query = query?.toQueryModel() ?: QueryModel(0.0)
)

fun QueryRemote.toQueryModel() = QueryModel(
  amount = amount ?: 0.0,
)

fun HistoryRemote.toHistoryModel() = HistoryModel(
  base = base ?: "",
  rates = rates ?: mapOf()
)