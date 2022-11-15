package com.challenge.domain.repository

import com.challenge.domain.model.CurrenciesModel
import kotlinx.coroutines.flow.Flow

interface ConverterRepository {
  suspend fun getAllCurrencies(): CurrenciesModel
}
