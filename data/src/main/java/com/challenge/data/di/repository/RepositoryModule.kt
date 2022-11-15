package com.challenge.data.di.repository

import com.challenge.data.repositories.ConverterRepositoryImpl
import com.challenge.domain.repository.ConverterRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

  @Binds
  abstract fun bindConverterRepository(impl: ConverterRepositoryImpl): ConverterRepository
}
