package com.challenge.domain.usecase

import com.challenge.domain.extensions.resultOf
import com.challenge.domain.model.CurrenciesModel
import com.challenge.domain.repository.ConverterRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.retryWhen
import java.io.IOException

private const val RETRY_TIME_IN_MILLIS = 15_000L

fun interface GetCurrenciesUseCase : suspend () -> Flow<Result<CurrenciesModel>>

suspend fun getCurrencies(
  converterRepository: ConverterRepository
): Flow<Result<CurrenciesModel>> = converterRepository
  .getAllCurrencies()
  .map {
    resultOf { it }
  }
//  .retryWhen { cause, _ ->
//    if (cause is IOException) {
//      emit(Result.failure(cause))
//
//      delay(RETRY_TIME_IN_MILLIS)
//      true
//    } else {
//      false
//    }
//  }
  .catch { // for other than IOException but it will stop collecting Flow
    emit(Result.failure(it)) // also catch does re-throw CancellationException
  }