package com.challenge.domain.model

data class ConversionModel(
  val query: QueryModel,

  val result: Double,
)

data class QueryModel(
  val amount: Double,
)