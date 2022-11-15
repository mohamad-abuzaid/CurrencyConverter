package com.challenge.data.repositories

import com.challenge.data.BuildConfig
import com.challenge.data.mappers.toCurrenciesModel
import com.challenge.domain.model.CurrenciesModel
import com.challenge.data.remote.api.ConverterApi
import com.challenge.domain.repository.ConverterRepository
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
