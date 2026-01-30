package com.example.demo

import com.example.common.vPrice
import com.example.common.validate
import java.time.LocalDate
import java.time.temporal.ChronoUnit


class BookingValidator(
    private val allowedCurrencies: Set<Currency>,
) {

    fun validateCreateBookingInput(input: CreateBookingInput) {
        validate("CreateBookingInput validation failed") {

            addIf(input.roomId.isBlank(), "roomId cannot be blank")
            addIf(input.userId.isBlank(), "userId cannot be blank")
            addIf(input.checkInDate > input.checkOutDate, "checkOutDate must be after checkInDate")
            addIf(input.checkInDate < LocalDate.now(), "checkInDate cannot be earlier than today")

            if (ChronoUnit.DAYS.between(input.checkInDate, input.checkOutDate) < 1) {
                add("booking period must be at least 1 day")
            }

            vPrice(input.price, input.currency, allowedCurrencies)
        }
    }
}


