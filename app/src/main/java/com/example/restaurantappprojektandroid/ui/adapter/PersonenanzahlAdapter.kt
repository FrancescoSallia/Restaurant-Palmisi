package com.example.restaurantappprojektandroid.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.restaurantappprojektandroid.ui.MainViewModel
import com.example.restuarantprojektapp.databinding.PersonenanzahlItemBinding

class PersonenanzahlAdapter(
    val dataset : List<Int>,
    val viewModel: MainViewModel
):RecyclerView.Adapter<PersonenanzahlAdapter.AnzahlGaeste>() {

    var selectedPosition = RecyclerView.NO_POSITION

    inner class AnzahlGaeste(val vb: PersonenanzahlItemBinding):RecyclerView.ViewHolder(vb.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnzahlGaeste {

        val vb = PersonenanzahlItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AnzahlGaeste(vb)
    }

    override fun getItemCount(): Int {

        return dataset.size
    }

    override fun onBindViewHolder(holder: AnzahlGaeste, position: Int) {

        val anzahl = dataset[position]

        //Der ganze block unten , ist für die farb markierten Personenanzahlen, wenn man draufklickt
        holder.vb.apply {
            tvPersonenanzahlNumber.text = anzahl.toString()
            root.isSelected = position == selectedPosition
        }

        holder.itemView.setOnClickListener {
            if (selectedPosition != position) {
                val previousSelected = selectedPosition
                selectedPosition = position

                //die anzahl wird weitergegeben um den wert raus zunehmen und im fragment zu sehen!
                viewModel.selectedPersonNumber(anzahl)

                notifyItemChanged(previousSelected)
                notifyItemChanged(selectedPosition)
                Log.i("TAG", "PersonenanzahlAdapter Selected position: $selectedPosition")

            }
        }
    }
    }
