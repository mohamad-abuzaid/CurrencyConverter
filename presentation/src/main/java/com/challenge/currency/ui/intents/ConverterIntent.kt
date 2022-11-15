package com.challenge.currency.ui.intents

sealed class ConverterIntent {
    object GetCurrencies : ConverterIntent()
    object ConvertCurrencies : ConverterIntent()
}
