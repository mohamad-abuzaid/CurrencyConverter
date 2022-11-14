package com.challenge.currency.repository

import com.challenge.currency.model.ResponseModel
import kotlinx.coroutines.flow.Flow

interface ResponseRepository {
  fun getRockets(): Flow<List<ResponseModel>>
  suspend fun refreshRockets()
}
