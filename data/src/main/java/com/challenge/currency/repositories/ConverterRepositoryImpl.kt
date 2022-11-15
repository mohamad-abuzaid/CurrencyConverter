package com.challenge.currency.repositories

import com.challenge.currency.BuildConfig
import com.challenge.currency.mappers.toCurrenciesModel
import com.challenge.currency.model.CurrenciesModel
import com.challenge.currency.remote.api.ConverterApi
import com.challenge.currency.repository.ConverterRepository
import javax.inject.Inject

class ConverterRepositoryImpl @Inject constructor(
  private val converterApi: ConverterApi
) : ConverterRepository {

  override suspend fun getAllCurrencies(): CurrenciesModel {
    return converterApi
      .getAllCurrencies(BuildConfig.API_KEY)
      .toCurrenciesModel()
  }
}
