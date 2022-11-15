package com.challenge.currency.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.challenge.currency.ui.uistate.CurrencyUiState.FetchState
import com.challenge.currency.ui.uistate.CurrencyUiState.FetchState.Error
import com.challenge.currency.ui.uistate.CurrencyUiState.FetchState.Fetched
import com.challenge.currency.ui.uistate.CurrencyUiState.FetchState.Loading
import com.challenge.domain.usecase.GetCurrenciesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
  private val getCurrenciesUseCase: GetCurrenciesUseCase
) : ViewModel() {

  init {
    viewModelScope.launch {
      getRockets()
    }
  }

  private fun getRockets(): Flow<FetchState> = flow {
    getCurrenciesUseCase()
      .onStart {
        emit(Loading)
      }
      .collect { result ->
        result
          .onSuccess { currencies ->
            emit(Fetched(currencies.symbols))
          }
          .onFailure {
            emit(Error(it))
          }
      }
  }
}