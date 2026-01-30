package com.example.demo

import java.time.LocalDate


data class CreateBookingInput(
    val userId: String,
    val roomId: String,
    val checkInDate: LocalDate,
    val checkOutDate: LocalDate,
    val price: Int,
    val currency: Currency
)
