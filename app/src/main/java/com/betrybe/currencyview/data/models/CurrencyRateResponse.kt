package com.betrybe.currencyview.data.models
import kotlinx.serialization.Serializable

@Serializable
data class CurrencyRateResponse(
    val success: Boolean,
    val base: String,
    val date: String,
    val rates: Map<String, Double>
)
