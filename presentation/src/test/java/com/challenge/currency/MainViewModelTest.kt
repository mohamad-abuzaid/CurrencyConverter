package com.challenge.currency

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.challenge.currency.ui.main.MainViewModel
import com.challenge.currency.ui.uistate.CurrencyUiState
import com.challenge.domain.model.CurrenciesModel
import com.challenge.domain.usecase.ConvertCurrencyUseCase
import com.challenge.domain.usecase.GetCurrenciesUseCase
import com.challenge.domain.usecase.GetHistoryUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {
  @ExperimentalCoroutinesApi @get:Rule var mainCoroutineRule = MainCoroutineRule()

  @Mock lateinit var getCurrenciesUseCase: GetCurrenciesUseCase
  @Mock lateinit var convertCurrencyUseCase: ConvertCurrencyUseCase
  @Mock lateinit var getHistoryUseCase: GetHistoryUseCase

  private val savedStateHandle = SavedStateHandle()
  private val currencyUiState = CurrencyUiState()

  private lateinit var viewModel: MainViewModel

  @Before fun setUp() {
    viewModel = MainViewModel(
      getCurrenciesUseCase,
      convertCurrencyUseCase,
      getHistoryUseCase,
      savedStateHandle,
      currencyUiState
    )
  }

  @Test fun `fetchCurrencies() with access_token with Success`() = runTest {
    // Arrange
    val currenciesModel = CurrenciesModel(mapOf("AED" to "Emirates", "USD" to "America"))

    whenever(getCurrenciesUseCase()).thenReturn(
      flowOf(
        Result.success(currenciesModel)
      )
    )

    // Act
    viewModel.fetchCurrencies()

    // Assert
    viewModel.uiState.test {
      val actualItem = awaitItem()

      Assert.assertEquals(
        currenciesModel.symbols.keys, actualItem.currencies
      )
      Assert.assertFalse(actualItem.isLoading)
      Assert.assertFalse(actualItem.isError)
    }
  }
}