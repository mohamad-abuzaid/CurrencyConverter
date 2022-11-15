package com.challenge.currency.ui.main

import androidx.lifecycle.SavedStateHandle
import com.challenge.currency.base.BaseViewModel
import com.challenge.currency.ui.intents.ConverterIntent
import com.challenge.currency.ui.intents.ConverterIntent.ConvertCurrencies
import com.challenge.currency.ui.intents.ConverterIntent.GetCurrencies
import com.challenge.currency.ui.uistate.CurrencyUiState
import com.challenge.currency.ui.uistate.CurrencyUiState.FetchedState
import com.challenge.currency.ui.uistate.CurrencyUiState.FetchedState.Error
import com.challenge.currency.ui.uistate.CurrencyUiState.FetchedState.Fetched
import com.challenge.currency.ui.uistate.CurrencyUiState.FetchedState.Loading
import com.challenge.domain.usecase.GetCurrenciesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
  private val getCurrenciesUseCase: GetCurrenciesUseCase,
  savedStateHandle: SavedStateHandle,
  currencyInitialState: CurrencyUiState
) : BaseViewModel<CurrencyUiState, FetchedState, ConverterIntent>(
  savedStateHandle,
  currencyInitialState
) {

  init {
    fireIntent(GetCurrencies)
  }

  override fun mapIntents(intent: ConverterIntent): Flow<FetchedState> = when (intent) {
    is GetCurrencies -> getCurrencies()
    is ConvertCurrencies -> convertCurrencies()
  }

  override fun reduceUiState(
    previousState: CurrencyUiState,
    fetchedState: FetchedState
  ): CurrencyUiState = when (fetchedState) {
    is Loading -> previousState.copy(
      isLoading = true,
      isError = false
    )
    is Fetched -> previousState.copy(
      isLoading = false,
      currencies = fetchedState.currencies,
      isError = false
    )
    is Error -> previousState.copy(
      isLoading = false,
      isError = true
    )
  }

  private fun getCurrencies(): Flow<FetchedState> = flow {
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

  private fun convertCurrencies(): Flow<FetchedState> = flow {
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