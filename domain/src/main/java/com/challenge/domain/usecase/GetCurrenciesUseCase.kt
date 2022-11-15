package com.challenge.domain.usecase

import com.challenge.domain.extensions.resultOf
import com.challenge.domain.model.CurrenciesModel
import com.challenge.domain.repository.ConverterRepository
import javax.inject.Inject

class GetCurrenciesUseCase @Inject constructor(
  private val converterRepository: ConverterRepository
) {

  suspend operator fun invoke(): Result<CurrenciesModel> = resultOf {
    converterRepository.getAllCurrencies()
  }

}