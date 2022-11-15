package com.challenge.currency.di.repository

import com.challenge.currency.repositories.ConverterRepositoryImpl
import com.challenge.currency.repository.ConverterRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

  @Binds
  @Singleton
  abstract fun bindConverterRepository(impl: ConverterRepositoryImpl): ConverterRepository
}
