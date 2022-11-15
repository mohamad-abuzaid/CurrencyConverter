package com.challenge.currency.usecase

import com.challenge.currency.extensions.resultOf
import com.challenge.currency.model.CurrenciesModel
import com.challenge.currency.repository.ConverterRepository
import javax.inject.Inject

class GetCurrenciesUseCase @Inject constructor(
  private val converterRepository: ConverterRepository
) {

  suspend operator fun invoke(): Result<CurrenciesModel> = resultOf {
    converterRepository.getAllCurrencies()
  }

}