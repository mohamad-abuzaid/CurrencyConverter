package com.challenge.currency

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.challenge.currency.ui.main.MainViewModel
import com.challenge.currency.ui.uistate.CurrencyUiState
import com.challenge.domain.extensions.resultOf
import com.challenge.domain.model.CurrenciesModel
import com.challenge.domain.usecase.ConvertCurrencyUseCase
import com.challenge.domain.usecase.GetCurrenciesUseCase
import com.challenge.domain.usecase.GetHistoryUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {
  @get:Rule var instantExecutorRule = InstantTaskExecutorRule()

  private val mainThreadSurrogate = newSingleThreadContext("UI thread")

  @Mock lateinit var getCurrenciesUseCase: GetCurrenciesUseCase
  @Mock lateinit var convertCurrencyUseCase: ConvertCurrencyUseCase
  @Mock lateinit var getHistoryUseCase: GetHistoryUseCase

  private val savedStateHandle = SavedStateHandle()
  private val currencyUiState = CurrencyUiState()

  private lateinit var viewModel: MainViewModel

  @Before
  fun setUp() {
    Dispatchers.setMain(mainThreadSurrogate)

    viewModel = MainViewModel(
      getCurrenciesUseCase,
      convertCurrencyUseCase,
      getHistoryUseCase,
      savedStateHandle,
      currencyUiState
    )
  }

  @After
  fun tearDown() {
    Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher
    mainThreadSurrogate.close()
  }

  @Test
  fun `fetchCurrencies() with access_token with Success`() {
    runBlocking {
      // Arrange
      val currencies = CurrenciesModel(mapOf("AED" to "Emirates", "USD" to "America"))

      whenever(getCurrenciesUseCase()).thenReturn(flowOf(resultOf { currencies }))

      /* List to collect the results */
      val listOfEmittedResult = mutableListOf<String>()
      val job = launch(Dispatchers.Main) {
        listOfEmittedResult.addAll(viewModel.uiState.value.currencies.toList())
      }

      // Act
      viewModel.fetchCurrencies()

      // Assert
      Assert.assertTrue(viewModel.uiState.value.currencies.isNotEmpty())
      verify(getCurrenciesUseCase())

      job.cancel()
    }
  }
}