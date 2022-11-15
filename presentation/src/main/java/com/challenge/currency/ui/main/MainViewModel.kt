package com.challenge.currency.ui.main

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.challenge.currency.ui.uistate.CurrencyUiState
import com.challenge.currency.ui.uistate.CurrencyUiState.FetchedState
import com.challenge.currency.ui.uistate.CurrencyUiState.FetchedState.Error
import com.challenge.currency.ui.uistate.CurrencyUiState.FetchedState.Fetched
import com.challenge.currency.ui.uistate.CurrencyUiState.FetchedState.Loading
import com.challenge.domain.usecase.GetCurrenciesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.scan
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val SAVED_UI_STATE_KEY = "savedUiStateKey"
private const val TAG = "BaseViewModel"

@HiltViewModel
class MainViewModel @Inject constructor(
  private val getCurrenciesUseCase: GetCurrenciesUseCase,
  private val savedStateHandle: SavedStateHandle,
  currencyInitialState: CurrencyUiState
) : ViewModel() {

  val uiState = savedStateHandle.getStateFlow(SAVED_UI_STATE_KEY, currencyInitialState)

  fun fetchCurrencies() {
    viewModelScope.launch {
      getCurrencies()
        .scan(uiState.value, ::reduceUiState)
        .catch { Log.e(TAG, "", it) }
        .collect {
          savedStateHandle[SAVED_UI_STATE_KEY] = it
        }
    }
  }

  private fun reduceUiState(
    previousState: CurrencyUiState,
    fetchedState: FetchedState
  ): CurrencyUiState = when (fetchedState) {
    is Loading -> previousState.copy(
      isLoading = true,
      isError = false
    )
    is Fetched -> previousState.copy(
      isLoading = false,
      currencies = fetchedState.currencies.keys,
      isError = false,
      isApi = true
    )
    is Error -> previousState.copy(
      isLoading = false,
      isError = true
    )
  }

  fun updateCurrFrom(position: Int) {
    savedStateHandle[SAVED_UI_STATE_KEY] = uiState.value.copy(currFrom = position)
  }

  fun updateCurrTo(position: Int) {
    savedStateHandle[SAVED_UI_STATE_KEY] = uiState.value.copy(currTo = position)
  }

  fun updateCurrencies(currencySet: Set<String>) {
    savedStateHandle[SAVED_UI_STATE_KEY] =
      uiState.value.copy(currencies = currencySet, isApi = false)
  }

  private fun getCurrencies(): Flow<FetchedState> = flow {
    getCurrenciesUseCase()
      .onStart { emit(Loading) }
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