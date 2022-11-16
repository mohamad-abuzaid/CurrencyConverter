package com.challenge.currency.ui.mappers

import com.challenge.currency.ui.model.ConversionDisplay
import com.challenge.currency.ui.model.HistoryDisplay
import com.challenge.currency.ui.model.QueryDisplay
import com.challenge.domain.model.ConversionModel
import com.challenge.domain.model.HistoryModel
import com.challenge.domain.model.QueryModel

fun ConversionModel.toConversionDisplay() = ConversionDisplay(
  result = result,
  query = query.toQueryDisplay()
)

fun QueryModel.toQueryDisplay() = QueryDisplay(
  amount = amount,
)

fun HistoryModel.toHistoryDisplay() = HistoryDisplay(
  base = base,
  rates = rates
)