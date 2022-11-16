package com.challenge.currency

import com.challenge.domain.extensions.resultOf
import com.challenge.domain.model.CurrenciesModel
import com.challenge.domain.repository.ConverterRepository
import com.challenge.domain.usecase.getCurrencies
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ConverterUseCaseTest {

  @Mock lateinit var converterRepository: ConverterRepository
  private val currenciesModelObject = CurrenciesModel(mapOf("AED" to "Emirates", "USD" to "America"))

  @Test
  fun `GetCurrenciesUseCase invoke() will call getCurrencies() return CurrenciesModel`() {
    runBlocking {
      // act
      Mockito.`when`(converterRepository.getAllCurrencies())
        .thenReturn(flowOf(currenciesModelObject))

      getCurrencies(converterRepository)

      // assert
      Assert.assertEquals(
        flowOf(resultOf { currenciesModelObject }), getCurrencies(converterRepository)
      )
    }
  }
}