package com.challenge.currency.di.converter

import com.challenge.currency.di.converter.ConverterModule.BindsModule
import com.challenge.currency.remote.api.ConverterApi
import com.challenge.currency.repositories.ConverterRepositoryImpl
import com.challenge.currency.repository.ConverterRepository
import com.challenge.currency.usecase.GetCurrenciesUseCase
import com.challenge.currency.usecase.getCurrencies
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = [BindsModule::class])
@InstallIn(SingletonComponent::class)
object ConverterModule {

  @Provides
  @Singleton
  fun provideConverterApi(
    retrofit: Retrofit
  ): ConverterApi {
    return retrofit.create(ConverterApi::class.java)
  }

  @Provides
  fun provideGetCurrenciesUseCase(
    converterRepository: ConverterRepository
  ): GetCurrenciesUseCase {
    return GetCurrenciesUseCase {
      getCurrencies(converterRepository)
    }
  }

  @Module
  @InstallIn(SingletonComponent::class)
  interface BindsModule {

    @Binds
    @Singleton
    fun bindConverterRepository(impl: ConverterRepositoryImpl): ConverterRepository
  }
}
