package com.example.rideexpress.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.rideexpress.R
import com.example.rideexpress.models.Booking

class BookingsAdapter(
    private var items: MutableList<Booking> = mutableListOf(),
    private val onEdit: (Booking) -> Unit,
    private val onDelete: (Booking) -> Unit
) : RecyclerView.Adapter<BookingsAdapter.BookingViewHolder>() {

    fun setItems(newItems: List<Booking>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookingViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_booking, parent, false)
        return BookingViewHolder(v)
    }

    override fun onBindViewHolder(holder: BookingViewHolder, position: Int) {
        val b = items[position]
        holder.bind(b)
        holder.btnEdit.setOnClickListener { onEdit(b) }
        holder.btnDelete.setOnClickListener { onDelete(b) }
        holder.itemView.setOnClickListener { onEdit(b) }
    }

    override fun getItemCount(): Int = items.size

    class BookingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvPickupDrop: TextView = itemView.findViewById(R.id.tvPickupDrop)
        private val tvTimeAndFare: TextView = itemView.findViewById(R.id.tvTimeAndFare)
        val btnEdit: Button = itemView.findViewById(R.id.btnEdit)
        val btnDelete: Button = itemView.findViewById(R.id.btnDelete)

        fun bind(b: Booking) {
            tvPickupDrop.text = "${b.pickupLocation} → ${b.dropoffLocation}"
            tvTimeAndFare.text = "${b.pickupTime} • ₹${b.estimatedFare}"
        }
    }
}

