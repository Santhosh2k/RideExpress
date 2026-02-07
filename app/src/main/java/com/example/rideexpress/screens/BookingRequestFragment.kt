package com.example.rideexpress.screens

import android.app.TimePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.rideexpress.R
import com.example.rideexpress.models.Booking
import com.example.rideexpress.viewmodel.BookingViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class BookingRequestFragment : Fragment() {

    private lateinit var bookingViewModel: BookingViewModel
    private lateinit var etPickupLocation: EditText
    private lateinit var etDropoffLocation: EditText
    private lateinit var etPickupTime: EditText
    private lateinit var tvEstimatedFare: TextView
    private lateinit var btnRequestRide: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_booking_request, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeViews(view)
        setupViewModel()
        setupListeners()
    }

    private fun initializeViews(view: View) {
        etPickupLocation = view.findViewById(R.id.etPickupLocation)
        etDropoffLocation = view.findViewById(R.id.etDropoffLocation)
        etPickupTime = view.findViewById(R.id.etPickupTime)
        tvEstimatedFare = view.findViewById(R.id.tvEstimatedFare)
        btnRequestRide = view.findViewById(R.id.btnRequestRide)
    }

    private fun setupViewModel() {
        bookingViewModel = ViewModelProvider(this).get(BookingViewModel::class.java)
    }

    private fun setupListeners() {
        etPickupTime.setOnClickListener {
            showTimePicker()
        }

        btnRequestRide.setOnClickListener {
            requestRide()
        }

        etPickupLocation.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                calculateEstimatedFare()
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        etDropoffLocation.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                calculateEstimatedFare()
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun showTimePicker() {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(
            requireContext(),
            { _, selectedHour, selectedMinute ->
                val timeFormat = String.format("%02d:%02d", selectedHour, selectedMinute)
                etPickupTime.setText(timeFormat)
            },
            hour,
            minute,
            true
        )
        timePickerDialog.show()
    }

    private fun calculateEstimatedFare() {
        val pickup = etPickupLocation.text.toString()
        val dropoff = etDropoffLocation.text.toString()

        if (pickup.isNotEmpty() && dropoff.isNotEmpty()) {
            val estimatedFare = (50..500).random().toDouble()
            tvEstimatedFare.text = "Estimated Fare: ₹$estimatedFare"
        }
    }

    private fun requestRide() {
        val pickup = etPickupLocation.text.toString().trim()
        val dropoff = etDropoffLocation.text.toString().trim()
        val pickupTime = etPickupTime.text.toString().trim()

        if (pickup.isEmpty() || dropoff.isEmpty() || pickupTime.isEmpty()) {
            Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val estimatedFare = tvEstimatedFare.text.toString()
            .replace("Estimated Fare: ₹", "").toDoubleOrNull() ?: 0.0

        val booking = Booking(
            userId = "user123",
            pickupLocation = pickup,
            dropoffLocation = dropoff,
            pickupTime = pickupTime,
            estimatedFare = estimatedFare,
            status = "pending",
            createdAt = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())
        )

        Log.e("testingLog","$booking")

        bookingViewModel.createBooking(booking)
        observeBookingResponse()
    }

    private fun observeBookingResponse() {
        bookingViewModel.bookingResponse.observe(viewLifecycleOwner) { response ->
            Log.e("testingLog in observer","$response")
            if (response.success) {
                Toast.makeText(requireContext(), "Booking successful!", Toast.LENGTH_SHORT).show()
                clearFields()
                parentFragmentManager.popBackStack()
            } else {
                Toast.makeText(requireContext(), "Error: ${response.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun clearFields() {
        etPickupLocation.text.clear()
        etDropoffLocation.text.clear()
        etPickupTime.text.clear()
        tvEstimatedFare.text = "Estimated Fare: ₹0"
    }
}