package com.example.restaurantappprojektandroid.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.restaurantappprojektandroid.model.Personenanzahl
import com.example.restaurantappprojektandroid.ui.MainViewModel
import com.example.restuarantprojektapp.databinding.PersonenanzahlItemBinding

class PersonenanzahlAdapter(
    val dataset : List<Int>,
    val viewModel: MainViewModel
):RecyclerView.Adapter<PersonenanzahlAdapter.AnzahlGaeste>() {
    inner class AnzahlGaeste(val vb: PersonenanzahlItemBinding):RecyclerView.ViewHolder(vb.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnzahlGaeste {

        val vb = PersonenanzahlItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AnzahlGaeste(vb)
    }

    override fun getItemCount(): Int {

        return dataset.size
    }

    override fun onBindViewHolder(holder: AnzahlGaeste, position: Int) {

        val anzahl = dataset[position]

        holder.vb.tvPersonenanzahlNumber.text = anzahl.toString()
    }
}