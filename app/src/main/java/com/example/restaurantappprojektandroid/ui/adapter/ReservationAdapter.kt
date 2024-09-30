package com.example.restaurantappprojektandroid.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.graphics.toColor
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.restaurantappprojektandroid.data.model.Reservation
import com.example.restaurantappprojektandroid.ui.MainViewModel
import com.example.restuarantprojektapp.R
import com.example.restuarantprojektapp.databinding.ReservationItemBinding

class ReservationAdapter(
    val dataset: List<Reservation>,
    val viewModel: MainViewModel,
    val context: Context
) : RecyclerView.Adapter<ReservationAdapter.ReservationViewHolder>() {

    inner class ReservationViewHolder(val vb: ReservationItemBinding) :
        RecyclerView.ViewHolder(vb.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReservationViewHolder {
        val vb: ReservationItemBinding =
            ReservationItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReservationViewHolder(vb)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: ReservationViewHolder, position: Int) {

        val reservation = dataset[position]
        val datum = reservation.datum

        holder.vb.tvPersonanzahl.text = "Personen: ${reservation.gaeste}"
        holder.vb.tvWann.text =  "Wann: ${datum}"
        holder.vb.tvBestTigung.text = reservation.getApprovedReservation(context).name
        holder.vb.tvBestTigung.setTextColor(reservation.getApprovedReservation(context).color)
        holder.vb.ivRandomImg.setImageResource(reservation.imgId)

        holder.itemView.setOnClickListener {
            viewModel.getDataUser()
            viewModel.getReservations(reservation.reservationId)
            val navController = holder.itemView.findNavController()
            navController.navigate(R.id.reservationDetailFragment)
        }

    }
}