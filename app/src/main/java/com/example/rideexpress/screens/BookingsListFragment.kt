package com.example.rideexpress.screens

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rideexpress.R
import com.example.rideexpress.adapters.BookingsAdapter
import com.example.rideexpress.models.Booking
import com.example.rideexpress.viewmodel.BookingViewModel

class BookingsListFragment : Fragment() {

    private lateinit var viewModel: BookingViewModel
    private lateinit var rvBookings: RecyclerView
    private lateinit var tvEmpty: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var adapter: BookingsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bookings_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(BookingViewModel::class.java)

        rvBookings = view.findViewById(R.id.rvBookings)
        tvEmpty = view.findViewById(R.id.tvEmpty)
        progressBar = view.findViewById(R.id.progressBar)

        adapter = BookingsAdapter(mutableListOf(), onEdit = { booking ->
//            openEditBooking(booking)
        }, onDelete = { booking ->
            confirmDelete(booking)
        })

        rvBookings.layoutManager = LinearLayoutManager(requireContext())
        rvBookings.adapter = adapter

        observeViewModel()

        // Load bookings
        viewModel.getAllBookings()
    }

    private fun observeViewModel() {
        viewModel.isLoading.observe(viewLifecycleOwner) { loading ->
            progressBar.visibility = if (loading) View.VISIBLE else View.GONE
        }

        viewModel.bookingsListResponse.observe(viewLifecycleOwner) { response ->
            if (response.success && response.data != null) {
                val list = response.data as List<Booking>
                adapter.setItems(list)
                tvEmpty.visibility = if (list.isEmpty()) View.VISIBLE else View.GONE
            } else {
                tvEmpty.visibility = View.VISIBLE
            }
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { msg ->
            if (!msg.isNullOrEmpty()) {
                AlertDialog.Builder(requireContext())
                    .setTitle("Error")
                    .setMessage(msg)
                    .setPositiveButton("OK", null)
                    .show()
            }
        }

        viewModel.deleteResponse.observe(viewLifecycleOwner) { resp ->
            if (resp.success) {
                // refresh list
                viewModel.getAllBookings()
            }
        }
    }

   /* private fun openEditBooking(booking: Booking) {
        // Launch booking request fragment with booking details for editing
        val frag = BookingRequestFragment.newInstance(booking)
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, frag)
            .addToBackStack(null)
            .commit()
    }*/

    private fun confirmDelete(booking: Booking) {
        AlertDialog.Builder(requireContext())
            .setTitle("Delete booking")
            .setMessage("Are you sure you want to delete this booking?")
            .setPositiveButton("Delete") { _, _ ->
                viewModel.deleteBooking(booking.id)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}

