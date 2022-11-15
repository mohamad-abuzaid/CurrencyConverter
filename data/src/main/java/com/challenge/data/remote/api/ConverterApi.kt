package com.challenge.data.remote.api

import com.challenge.data.remote.models.CurrenciesRemote
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ConverterApi {

  @GET("symbols")
  suspend fun getAllCurrencies(
    @Header("apikey") accessKey: String,
  ): CurrenciesRemote
}
