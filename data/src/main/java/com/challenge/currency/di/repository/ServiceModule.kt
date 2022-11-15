package com.challenge.currency.di.repository

import com.challenge.currency.remote.api.ConverterApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

  @Provides
  @Singleton
  fun provideConverterApi(
    retrofit: Retrofit
  ): ConverterApi {
    return retrofit.create(ConverterApi::class.java)
  }
}
