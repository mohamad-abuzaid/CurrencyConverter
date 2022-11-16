package com.challenge.domain.repository

import com.challenge.domain.model.ConversionModel
import com.challenge.domain.model.CurrenciesModel
import com.challenge.domain.model.HistoryModel
import kotlinx.coroutines.flow.Flow

interface ConverterRepository {
  suspend fun getAllCurrencies(): Flow<CurrenciesModel>

  suspend fun convertCurrency(
    to: String,
    from: String,
    amount: Double
  ): Flow<ConversionModel>

  suspend fun getRatesHistory(
    startDate: String,
    endDate: String,
    base: String,
    symbols: String,
  ): Flow<HistoryModel>
}
