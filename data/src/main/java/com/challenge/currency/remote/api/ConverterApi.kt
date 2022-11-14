package com.challenge.currency.remote.api

import com.challenge.currency.remote.models.ResponseRemote
import retrofit2.http.GET

interface ConverterApi {

    @GET("rockets")
    suspend fun getRockets(): List<ResponseRemote>
}
