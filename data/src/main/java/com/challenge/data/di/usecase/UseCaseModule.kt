package com.challenge.data.di.usecase

import com.challenge.domain.repository.ConverterRepository
import com.challenge.domain.usecase.ConvertCurrencyUseCase
import com.challenge.domain.usecase.GetCurrenciesUseCase
import com.challenge.domain.usecase.GetHistoryUseCase
import com.challenge.domain.usecase.convertCurrency
import com.challenge.domain.usecase.getCurrencies
import com.challenge.domain.usecase.getHistory
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

  @Provides
  fun provideGetHistoryUseCase(
    converterRepository: ConverterRepository
  ): GetHistoryUseCase {
    return GetHistoryUseCase { startDate, endDate, base, symbols ->
      getHistory(converterRepository, startDate, endDate, base, symbols)
    }
  }
}
