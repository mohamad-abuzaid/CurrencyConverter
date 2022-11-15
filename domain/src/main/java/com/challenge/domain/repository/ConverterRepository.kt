package com.challenge.domain.repository

import com.challenge.domain.model.ConversionModel
import com.challenge.domain.model.CurrenciesModel
import kotlinx.coroutines.flow.Flow

interface ConverterRepository {
  suspend fun getAllCurrencies(): Flow<CurrenciesModel>
  suspend fun convertCurrency(
    to: String,
    from: String,
    amount: Double
  ): Flow<ConversionModel>
}
