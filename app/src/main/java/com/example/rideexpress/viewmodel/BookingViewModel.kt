package com.example.rideexpress.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rideexpress.models.ApiResponse
import com.example.rideexpress.models.Booking
import com.example.rideexpress.repository.BookingRepository
import kotlinx.coroutines.launch

class BookingViewModel : ViewModel() {
    private val repository = BookingRepository()

    private val _bookingResponse = MutableLiveData<ApiResponse<Booking>>()
    val bookingResponse: LiveData<ApiResponse<Booking>> = _bookingResponse

    private val _bookingsListResponse = MutableLiveData<ApiResponse<List<Booking>>>()
    val bookingsListResponse: LiveData<ApiResponse<List<Booking>>> = _bookingsListResponse

    private val _deleteResponse = MutableLiveData<ApiResponse<String>>()
    val deleteResponse: LiveData<ApiResponse<String>> = _deleteResponse

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun createBooking(booking: Booking) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val response = repository.createBooking(booking)
                _bookingResponse.value = response
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "Unknown error occurred"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getAllBookings() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val response = repository.getAllBookings()
                _bookingsListResponse.value = response
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "Unknown error occurred"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun deleteBooking(id: String) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val response = repository.deleteBooking(id)
                _deleteResponse.value = response
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "Unknown error occurred"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
