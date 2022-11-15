package com.challenge.data.repositories

import com.challenge.data.BuildConfig
import com.challenge.data.mappers.toCurrenciesModel
import com.challenge.data.remote.api.ConverterApi
import com.challenge.domain.model.CurrenciesModel
import com.challenge.domain.repository.ConverterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ConverterRepositoryImpl @Inject constructor(
  private val converterApi: ConverterApi
) : ConverterRepository {

  override suspend fun getAllCurrencies(): Flow<CurrenciesModel> = flow {
    emit(
      converterApi
        .getAllCurrencies(BuildConfig.API_KEY).toCurrenciesModel()
    )
  }
}
