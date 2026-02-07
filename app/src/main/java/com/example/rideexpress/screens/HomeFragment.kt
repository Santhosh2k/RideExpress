package com.example.rideexpress.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.rideexpress.R

class HomeFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1. Find the TextView by ID
        val tvAboutUs = view.findViewById<TextView>(R.id.tvAboutUs)

        // 2. Set the Click Listener
        tvAboutUs.setOnClickListener {
            val aboutUsFragment = AboutUsFragment()

            // 3. Perform the fragment transaction
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, aboutUsFragment)
                .addToBackStack(null)
                .commit()
        }

        // Add BookingRequestFragment navigation
        val card1 = view.findViewById<View>(R.id.card1)
        card1.setOnClickListener {
            val bookingRequestFragment = BookingRequestFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, bookingRequestFragment)
                .addToBackStack(null)
                .commit()
        }

        // Add Book Ride Now button
        val btnBookRideNow = view.findViewById<Button>(R.id.btnBookRideNow)
        btnBookRideNow.setOnClickListener {
            val bookingRequestFragment = BookingRequestFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, bookingRequestFragment)
                .addToBackStack(null)
                .commit()
        }

        val btnViewBookings = view.findViewById<Button>(R.id.btnViewBookings)
        btnViewBookings.setOnClickListener {
            val bookingView = BookingsListFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, bookingView)
                .addToBackStack(null)
                .commit()
        }
    }

}