package com.challenge.currency.usecase

import com.challenge.currency.model.ResponseModel
import com.challenge.currency.repository.ResponseRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.retryWhen
import java.io.IOException

private const val RETRY_TIME_IN_MILLIS = 15_000L

fun interface GetRocketsUseCase : () -> Flow<Result<List<ResponseModel>>>

fun getRockets(
    responseRepository: ResponseRepository
): Flow<Result<List<ResponseModel>>> = responseRepository
  .getRockets()
  .map {
    resultOf { it }
  }
  .retryWhen { cause, _ ->
    if (cause is IOException) {
      emit(Result.failure(cause))

      delay(RETRY_TIME_IN_MILLIS)
      true
    } else {
      false
    }
  }
  .catch { // for other than IOException but it will stop collecting Flow
    emit(Result.failure(it)) // also catch does re-throw CancellationException
  }
