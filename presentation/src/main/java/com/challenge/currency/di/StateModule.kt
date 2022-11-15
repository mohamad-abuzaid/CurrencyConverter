package com.challenge.currency.di

import com.challenge.currency.ui.uistate.CurrencyUiState
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object CurrencyViewModelModule {

  @Provides
  fun provideInitialCurrencyUiState(): CurrencyUiState = CurrencyUiState()
}