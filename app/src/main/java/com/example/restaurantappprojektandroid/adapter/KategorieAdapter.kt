package com.example.restaurantappprojektandroid.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.restaurantappprojektandroid.model.Category
import com.example.restaurantappprojektandroid.ui.MainViewModel
import com.example.restuarantprojektapp.databinding.ItemCategorieBinding

class KategorieAdapter(
    private val dataset: List<Category>,
    private val viewModel: MainViewModel
):RecyclerView.Adapter<KategorieAdapter.CategorieViewHolder>() {

    inner class CategorieViewHolder(val binding:ItemCategorieBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategorieViewHolder {

        val binding = ItemCategorieBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return CategorieViewHolder(binding)
    }

    override fun getItemCount(): Int {
       return dataset.size
    }

    override fun onBindViewHolder(holder: CategorieViewHolder, position: Int) {
        val categorie = dataset[position]

        holder.binding.tvKategorieName.text = categorie.categorieName

        holder.binding.root.setOnClickListener {
            viewModel.getMealsByCategory(categorie.categorieName)
        }

    }

}