package com.challenge.currency

import app.cash.turbine.test
import com.challenge.domain.model.ConversionModel
import com.challenge.domain.model.CurrenciesModel
import com.challenge.domain.model.QueryModel
import com.challenge.domain.repository.ConverterRepository
import com.challenge.domain.usecase.convertCurrency
import com.challenge.domain.usecase.getCurrencies
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any

@RunWith(MockitoJUnitRunner::class)
class ConverterUseCaseTest {

  @Mock lateinit var converterRepository: ConverterRepository

  private val currenciesModelObject =
    CurrenciesModel(mapOf("AED" to "Emirates", "USD" to "America"))

  private val conversionModelObject =
    ConversionModel(QueryModel(5.5), 10.4)

  @Test
  fun `GetCurrenciesUseCase invoke() will call getCurrencies() return CurrenciesModel`() {
    runBlocking {
      // act
      Mockito.`when`(converterRepository.getAllCurrencies())
        .thenReturn(flowOf(currenciesModelObject))

      getCurrencies(converterRepository).test {
        val result = awaitItem()

        Assert.assertEquals(
          Result.success(currenciesModelObject),
          result
        )
        awaitComplete()
      }
    }
  }

  @Test
  fun `ConvertCurrencyUseCase invoke() will call convertCurrency() return ConversionModel`() {
    runBlocking {
      // act
      Mockito.`when`(converterRepository.convertCurrency(any(), any(), any()))
        .thenReturn(flowOf(conversionModelObject))

      convertCurrency(converterRepository,"AED", "USD", 22.4).test {
        val result = awaitItem()

        Assert.assertEquals(
          Result.success(conversionModelObject),
          result
        )
        awaitComplete()
      }
    }
  }
}