package com.example.rideexpress.api

import com.example.rideexpress.models.ApiResponse
import com.example.rideexpress.models.Booking
import retrofit2.http.*

interface BookingService {
    @GET("bookings")
    suspend fun getAllBookings(): ApiResponse<List<Booking>>

    @GET("bookings/{id}")
    suspend fun getBooking(@Path("id") id: String): ApiResponse<Booking>

    @POST("bookings")
    suspend fun createBooking(@Body booking: Booking): ApiResponse<Booking>

    @PUT("bookings/{id}")
    suspend fun updateBooking(
        @Path("id") id: String,
        @Body booking: Booking
    ): ApiResponse<Booking>

    @DELETE("bookings/{id}")
    suspend fun deleteBooking(@Path("id") id: String): ApiResponse<String>
}
