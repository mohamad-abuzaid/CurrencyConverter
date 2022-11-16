package com.challenge.currency.ui.main

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.challenge.currency.ui.mappers.toConversionDisplay
import com.challenge.currency.ui.mappers.toHistoryDisplay
import com.challenge.currency.ui.uistate.CurrencyUiState
import com.challenge.currency.ui.uistate.CurrencyUiState.FetchedState
import com.challenge.currency.ui.uistate.CurrencyUiState.FetchedState.Converted
import com.challenge.currency.ui.uistate.CurrencyUiState.FetchedState.Error
import com.challenge.currency.ui.uistate.CurrencyUiState.FetchedState.Fetched
import com.challenge.currency.ui.uistate.CurrencyUiState.FetchedState.History
import com.challenge.currency.ui.uistate.CurrencyUiState.FetchedState.Loading
import com.challenge.domain.usecase.ConvertCurrencyUseCase
import com.challenge.domain.usecase.GetCurrenciesUseCase
import com.challenge.domain.usecase.GetHistoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.scan
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val SAVED_UI_STATE_KEY = "savedUiStateKey"
private const val TAG = "MainViewModel"

@HiltViewModel
class MainViewModel @Inject constructor(
  private val getCurrenciesUseCase: GetCurrenciesUseCase,
  private val convertCurrencyUseCase: ConvertCurrencyUseCase,
  private val getHistoryUseCase: GetHistoryUseCase,
  private val savedStateHandle: SavedStateHandle,
  currencyInitialState: CurrencyUiState
) : ViewModel() {

  val uiState = savedStateHandle.getStateFlow(SAVED_UI_STATE_KEY, currencyInitialState)

  fun fetchCurrencies() {
    viewModelScope.launch {
      getCurrencies()
        .scan(uiState.value, ::reduceUiState)
        .catch { Log.e(TAG, "fetchCurrencies", it) }
        .collect {
          savedStateHandle[SAVED_UI_STATE_KEY] = it
        }
    }
  }

  fun fetchConversion(
    to: String,
    from: String,
    amount: Double
  ) {
    viewModelScope.launch {
      convertCurrency(to, from, amount)
        .scan(uiState.value, ::reduceUiState)
        .catch { Log.e(TAG, "fetchConversion", it) }
        .collect {
          savedStateHandle[SAVED_UI_STATE_KEY] = it
        }
    }
  }

  fun fetchHistoryRates(
    startDate: String,
    endDate: String,
    base: String,
    symbols: String
  ) {
    viewModelScope.launch {
      getRatesHistory(startDate, endDate, base, symbols)
        .scan(uiState.value, ::reduceUiState)
        .catch { Log.e(TAG, "fetchHistoryRates", it) }
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
    is Converted -> previousState.copy(
      isLoading = false,
      currFromVal = fetchedState.conversion.query.amount,
      currToVal = fetchedState.conversion.result,
      isError = false,
      isApi = true
    )
    is History -> previousState.copy(
      isLoading = false,
      history = fetchedState.history,
      isError = false,
      isApi = true
    )
    is Error -> previousState.copy(
      isLoading = false,
      isError = true
    )
  }

  fun updateCurrFrom(position: Int) {
    savedStateHandle[SAVED_UI_STATE_KEY] = uiState.value.copy(currFromPos = position)
  }

  fun updateCurrTo(position: Int) {
    savedStateHandle[SAVED_UI_STATE_KEY] = uiState.value.copy(currToPos = position)
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

  private fun convertCurrency(
    to: String,
    from: String,
    amount: Double
  ): Flow<FetchedState> = flow {
//    val query = QueryDisplay(25.0)
//    val conversion = ConversionDisplay(query, 33.4)
//    emit(Converted(conversion))

    convertCurrencyUseCase(to, from, amount)
      .onStart {
        emit(Loading)
      }
      .collect { result ->
        result
          .onSuccess { conversion ->
            emit(Converted(conversion.toConversionDisplay()))
          }
          .onFailure {
            emit(Error(it))
          }
      }
  }

  private fun getRatesHistory(
    startDate: String,
    endDate: String,
    base: String,
    symbols: String
  ): Flow<FetchedState> = flow {
    getHistoryUseCase(startDate, endDate, base, symbols)
      .onStart {
        emit(Loading)
      }
      .collect { result ->
        result
          .onSuccess { history ->
            emit(History(history.toHistoryDisplay()))
          }
          .onFailure {
            emit(Error(it))
          }
      }
  }
}