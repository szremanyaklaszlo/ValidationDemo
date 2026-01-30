package com.example.common

import com.example.demo.Currency


fun ValidationContext.vPrice(price: Int, currency: Currency, allowedCurrencies: Set<Currency>) {
    addIf(currency !in allowedCurrencies, "currency type is not in allowed")
    addIf(price < 0, "price cannot be less than 0")
}