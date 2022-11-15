package com.challenge.currency.base

import android.os.Parcelable
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.scan
import kotlinx.coroutines.launch

private const val SAVED_UI_STATE_KEY = "savedUiStateKey"

@OptIn(FlowPreview::class)
abstract class BaseViewModel<UI_STATE : Parcelable, FETCHED_UI_STATE, INTENT>(
  savedStateHandle: SavedStateHandle,
  initialState: UI_STATE
) : ViewModel() {
  val TAG = "BaseViewModel"

  private val intentFlow = MutableSharedFlow<INTENT>()
  val uiState = savedStateHandle.getStateFlow(SAVED_UI_STATE_KEY, initialState)

  init {
    viewModelScope.launch {
      intentFlow
        .flatMapMerge { mapIntents(it) }
        .scan(uiState.value, ::reduceUiState)
        .catch { Log.e(TAG, "", it) }
        .collect {
          savedStateHandle[SAVED_UI_STATE_KEY] = it
        }
    }
  }

  fun fireIntent(intent: INTENT) =
    viewModelScope.launch {
      intentFlow.emit(intent)
    }

  protected abstract fun mapIntents(intent: INTENT): Flow<FETCHED_UI_STATE>

  protected abstract fun reduceUiState(
    previousState: UI_STATE,
    fetchedState: FETCHED_UI_STATE
  ): UI_STATE
}
