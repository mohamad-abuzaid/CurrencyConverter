package com.challenge.data.repositories

import com.challenge.data.mappers.toConversionModel
import com.challenge.data.mappers.toCurrenciesModel
import com.challenge.data.remote.api.ConverterApi
import com.challenge.domain.BuildConfig
import com.challenge.domain.model.ConversionModel
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

  override suspend fun convertCurrency(
    to: String,
    from: String,
    amount: Double
  ): Flow<ConversionModel> = flow {
    emit(
      converterApi
        .convertCurrency(BuildConfig.API_KEY, to, from, amount).toConversionModel()
    )
  }
}
