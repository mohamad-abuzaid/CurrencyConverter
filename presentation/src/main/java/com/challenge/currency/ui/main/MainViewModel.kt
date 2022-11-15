package com.challenge.currency.ui.main

import androidx.lifecycle.ViewModel
import com.challenge.currency.repository.ConverterRepository
import com.challenge.currency.usecase.GetCurrenciesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
  private val getCurrenciesUseCase: GetCurrenciesUseCase
): ViewModel() {
  // TODO: Implement the ViewModel
}