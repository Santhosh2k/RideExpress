package com.example.rideexpress.repository

import com.example.rideexpress.api.RetrofitClient
import com.example.rideexpress.models.Booking

class BookingRepository {
    private val bookingService = RetrofitClient.getBookingService()

    suspend fun createBooking(booking: Booking) = bookingService.createBooking(booking)

    suspend fun getAllBookings() = bookingService.getAllBookings()

    suspend fun getBookingById(id: String) = bookingService.getBooking(id)

    suspend fun updateBooking(id: String, booking: Booking) =
        bookingService.updateBooking(id, booking)

    suspend fun deleteBooking(id: String) = bookingService.deleteBooking(id)
}
