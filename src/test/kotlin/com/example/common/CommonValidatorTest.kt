package com.example.common

import com.example.demo.Currency
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.shouldBe


class CommonValidatorTest : StringSpec() {

    private val allowedCurrencies = setOf(Currency.EUR, Currency.JPY)

    init {
        """vPrice When price and currency is valid, should not add any error""" {
            val validationContext = ValidationContext()
            validationContext.vPrice(1000, Currency.EUR, allowedCurrencies)

            validationContext.errors shouldBe emptyList()
        }

        """When price is below 0, should add an error to the context""" {
            val validationContext = ValidationContext()
            validationContext.vPrice(-1, Currency.EUR, allowedCurrencies)

            validationContext.errors shouldContain "price cannot be less than 0"
        }

        """When currency type is not allowed, should add an error to the context""" {
            val validationContext = ValidationContext()
            validationContext.vPrice(1000, Currency.USD, allowedCurrencies)

            validationContext.errors shouldContain "currency type is not in allowed"
        }
    }
}
