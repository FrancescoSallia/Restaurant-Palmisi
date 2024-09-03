package com.example.restaurantappprojektandroid.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.restaurantappprojektandroid.model.Category
import com.example.restuarantprojektapp.databinding.ItemKarussellBinding

class KategorieAdapter(
    private val dataset: List<Category>
):RecyclerView.Adapter<KategorieAdapter.CategorieViewHolder>() {

    inner class CategorieViewHolder(val binding:ItemKarussellBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategorieViewHolder {

        val binding = ItemKarussellBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return CategorieViewHolder(binding)
    }

    override fun getItemCount(): Int {
       return dataset.size
    }

    override fun onBindViewHolder(holder: CategorieViewHolder, position: Int) {
        holder.binding.tvKategorieName.text = dataset[position].strCategory
        Log.i("TAG", dataset[position].strCategory)
    }

}