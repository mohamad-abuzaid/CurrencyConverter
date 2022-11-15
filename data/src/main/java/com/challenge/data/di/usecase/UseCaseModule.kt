package com.challenge.data.di.usecase

import com.challenge.domain.repository.ConverterRepository
import com.challenge.domain.usecase.GetCurrenciesUseCase
import com.challenge.domain.usecase.getCurrencies
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent

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
}
