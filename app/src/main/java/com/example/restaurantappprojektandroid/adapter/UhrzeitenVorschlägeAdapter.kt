package com.example.restaurantappprojektandroid.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.restaurantappprojektandroid.ui.MainViewModel
import com.example.restuarantprojektapp.databinding.UhrzeitVorschlagItemBinding

class UhrzeitenVorschlägeAdapter(
    val dataset: List<String>,
    val viewModel: MainViewModel
) : RecyclerView.Adapter<UhrzeitenVorschlägeAdapter.UhrzeitenViewHolder>() {
    inner class UhrzeitenViewHolder(val vb: UhrzeitVorschlagItemBinding) :
        RecyclerView.ViewHolder(vb.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UhrzeitenViewHolder {
        val vb: UhrzeitVorschlagItemBinding =
            UhrzeitVorschlagItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UhrzeitenViewHolder(vb)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: UhrzeitenViewHolder, position: Int) {
        val uhrzeit = viewModel.ReservationDatasources.loadUhrzeiten()[position]

        holder.vb.tvZeitNumber.text =  uhrzeit

    }


}