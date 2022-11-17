package com.challenge.currency

import app.cash.turbine.test
import com.challenge.data.remote.api.ConverterApi
import com.challenge.data.repositories.ConverterRepositoryImpl
import com.challenge.domain.repository.ConverterRepository
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@RunWith(MockitoJUnitRunner::class)
class ConverterRepositoryImplTest {

  private val server: MockWebServer = MockWebServer()
  private val MOCK_WEBSERVER_PORT = 8000
  private lateinit var converterApi: ConverterApi
  private lateinit var converterRepository: ConverterRepository

  @Before
  fun setup() {
    server.start(MOCK_WEBSERVER_PORT)
    converterApi = Retrofit.Builder()
      .baseUrl(server.url("/"))
      .addConverterFactory(GsonConverterFactory.create(Gson()))
      .build()
      .create(ConverterApi::class.java)
    converterRepository = ConverterRepositoryImpl(converterApi)
  }

  @After
  fun shutdown() {
    server.shutdown()
  }

  @Test
  fun `getAllCurrencies API parse success`() {
    runBlocking {
      server.apply {
        enqueue(
          MockResponse().setBody(
            MockResponseFileReader(
              "converter/CurrenciesResponseSuccess.json"
            ).content
          )
        )
      }

      converterRepository.getAllCurrencies()
        .test {
          val actualItem = awaitItem()

          Assert.assertEquals(
            "United Arab Emirates Dirham", actualItem.symbols["AED"]
          )
        }
    }
  }

  @Test
  fun `getAllCurrencies API parse fail`() {
    runBlocking {
      server.apply {
        enqueue(
          MockResponse().setBody(
            MockResponseFileReader("converter/CurrenciesResponseError.json").content
          )
        )
      }

      converterRepository.getAllCurrencies()
        .test {
          awaitError()
        }
    }
  }
}