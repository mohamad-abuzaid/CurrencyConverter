package com.challenge.currency.usecase

import com.challenge.currency.extensions.resultOf
import com.challenge.currency.model.CurrenciesModel
import com.challenge.currency.repository.ConverterRepository

fun interface GetCurrenciesUseCase : suspend () -> Result<CurrenciesModel>

suspend fun getCurrencies(
  converterRepository: ConverterRepository
): Result<CurrenciesModel> = resultOf {
  converterRepository.getAllCurrencies()
}