package com.challenge.currency.ui.main

import androidx.lifecycle.ViewModel
import com.challenge.domain.repository.ConverterRepository
import com.challenge.domain.usecase.GetCurrenciesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
  private val getCurrenciesUseCase: GetCurrenciesUseCase
): ViewModel() {
  // TODO: Implement the ViewModel
}