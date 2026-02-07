package com.example.rideexpress.models

data class Booking(
    val id: String = "",
    val userId: String = "",
    val pickupLocation: String = "",
    val dropoffLocation: String = "",
    val pickupTime: String = "",
    val estimatedFare: Double = 0.0,
    val status: String = "pending", // pending, confirmed, completed, cancelled
    val createdAt: String = "",
    val driverName: String? = null,
    val carDetails: String? = null
)

/*data class ApiResponse<T>(
    val success: Boolean = false,
    val message: String = "",
    val data: T? = null
)*/
