package com.challenge.domain.model

data class HistoryModel(
  val base: String,

  val rates: Map<String, Map<String, String>?>,
)