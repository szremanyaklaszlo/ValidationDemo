package com.example.demo

import com.example.common.InputValidationException
import com.example.common.ValidationContext
import com.example.common.uuid
import com.example.common.vPrice
import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.shouldBe
import io.mockk.*
import java.time.LocalDate


class BookingValidatorTest : StringSpec() {

    private val allowedCurrencies = setOf(Currency.JPY, Currency.EUR)
    private val bookingValidator = BookingValidator(allowedCurrencies)

    private val input = CreateBookingInput(
        roomId = uuid(),
        userId = uuid(),
        currency = Currency.EUR,
        price = 1000,
        checkInDate = LocalDate.now().plusDays(2),
        checkOutDate = LocalDate.now().plusDays(3)
    )

    init {
        """When booking is valid, should not throw any exceptions""" {
            shouldNotThrowAny { bookingValidator.validateCreateBookingInput(input) }
        }

        """During validation price check should be called.""" {
            mockkStatic("com.example.common.CommonValidatorKt")

            try {
                every {
                    any<ValidationContext>().vPrice(
                        price = any(),
                        currency = any(),
                        allowedCurrencies = any()
                    )
                } just Runs

                bookingValidator.validateCreateBookingInput(input)

                verify(exactly = 1) {
                    any<ValidationContext>().vPrice(
                        price = input.price,
                        currency = input.currency,
                        allowedCurrencies = allowedCurrencies
                    )
                }
            } finally {
                unmockkStatic("com.example.common.CommonValidatorKt")
            }
        }

        """When roomId is blank, should throw InputValidationException""" {
            val exception = shouldThrow<InputValidationException> {
                bookingValidator.validateCreateBookingInput(input.copy(roomId = ""))
            }

            exception.message shouldBe "CreateBookingInput validation failed"
            exception.errors shouldContain "roomId cannot be blank"
        }

        """When userId is blank, should throw InputValidationException""" {
            val exception = shouldThrow<InputValidationException> {
                bookingValidator.validateCreateBookingInput(input.copy(userId = ""))
            }

            exception.message shouldBe "CreateBookingInput validation failed"
            exception.errors shouldContain "userId cannot be blank"
        }

        """When checkOutDate is not after checkInDate, should throw InputValidationException""" {
            val exception = shouldThrow<InputValidationException> {
                bookingValidator.validateCreateBookingInput(
                    input.copy(
                        checkInDate = LocalDate.now().plusDays(3),
                        checkOutDate = LocalDate.now().plusDays(2)
                    )
                )
            }

            exception.message shouldBe "CreateBookingInput validation failed"
            exception.errors shouldContain "checkOutDate must be after checkInDate"
        }

        """When booking period is less than a day, should throw InputValidationException""" {
            val exception = shouldThrow<InputValidationException> {
                bookingValidator.validateCreateBookingInput(
                    input.copy(
                        checkInDate = LocalDate.now().plusDays(2),
                        checkOutDate = LocalDate.now().plusDays(2)
                    )
                )
            }

            exception.message shouldBe "CreateBookingInput validation failed"
            exception.errors shouldContain "booking period must be at least 1 day"
        }

        """When checkInDay is before today, should throw InputValidationException""" {
            val exception = shouldThrow<InputValidationException> {
                bookingValidator.validateCreateBookingInput(
                    input.copy(
                        checkInDate = LocalDate.now().minusDays(2)
                    )
                )
            }

            exception.message shouldBe "CreateBookingInput validation failed"
            exception.errors shouldContain "checkInDate cannot be earlier than today"
        }
    }
}
