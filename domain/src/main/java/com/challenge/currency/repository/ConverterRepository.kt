package com.challenge.currency.repository

import com.challenge.currency.model.CurrenciesModel
import kotlinx.coroutines.flow.Flow

interface ConverterRepository {
  suspend fun getAllCurrencies(): CurrenciesModel
}
