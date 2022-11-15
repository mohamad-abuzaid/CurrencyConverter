package com.challenge.data.di.usecase

import com.challenge.domain.repository.ConverterRepository
import com.challenge.domain.usecase.ConvertCurrencyUseCase
import com.challenge.domain.usecase.GetCurrenciesUseCase
import com.challenge.domain.usecase.convertCurrency
import com.challenge.domain.usecase.getCurrencies
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

  @Provides
  fun provideGetCurrenciesUseCase(
    converterRepository: ConverterRepository
  ): GetCurrenciesUseCase {
    return GetCurrenciesUseCase {
      getCurrencies(converterRepository)
    }
  }

  @Provides
  fun provideConvertCurrencyUseCase(
    converterRepository: ConverterRepository
  ): ConvertCurrencyUseCase {
    return ConvertCurrencyUseCase { to, from, amount ->
      convertCurrency(converterRepository, to, from, amount)
    }
  }
}
