package com.example.restaurantappprojektandroid.ui.adapter

import android.util.Log
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
     var selectedPosition = RecyclerView.NO_POSITION

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UhrzeitenViewHolder {
        val vb: UhrzeitVorschlagItemBinding =
            UhrzeitVorschlagItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UhrzeitenViewHolder(vb)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: UhrzeitenViewHolder, position: Int) {

        val zeit = dataset[position]

        //Der ganze block unten , ist für die farb markierten uhrzeiten, wenn man draufklickt
        holder.vb.apply {
            holder.vb.tvZeitNumber.text =  zeit

            //die zeit variable wird weitergegeben um den wert raus zunehmen und im fragment zu sehen!


            holder.vb.root.isSelected = position == selectedPosition
        }

        holder.itemView.setOnClickListener {
           if (selectedPosition != position){
               val früherePosition = selectedPosition
               notifyItemChanged(früherePosition)
               selectedPosition = position
               notifyItemChanged(selectedPosition)
               viewModel.selectedTime(zeit)
           }
        }


    }


}