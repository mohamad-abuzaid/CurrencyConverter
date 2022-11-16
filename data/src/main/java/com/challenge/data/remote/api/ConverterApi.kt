package com.challenge.data.remote.api

import com.challenge.data.remote.models.ConversionRemote
import com.challenge.data.remote.models.CurrenciesRemote
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ConverterApi {

  @GET("symbols")
  suspend fun getAllCurrencies(
    @Header("apikey") accessKey: String,
  ): CurrenciesRemote

  @GET("convert")
  suspend fun convertCurrency(
    @Header("apikey") accessKey: String,
    @Query("to") to: String,
    @Query("from") from: String,
    @Query("amount") amount: Double,
  ): ConversionRemote

  @GET("timeseries")
  suspend fun getHistory(
    @Header("apikey") accessKey: String,
    @Query("start_date") startDate: String,
    @Query("end_date") endDate: String,
    @Query("base") base: String,
    @Query("symbols") symbols: String,
  ): ConversionRemote
}
