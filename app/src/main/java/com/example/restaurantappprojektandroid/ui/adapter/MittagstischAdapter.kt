package com.example.restaurantappprojektandroid.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.restaurantappprojektandroid.data.model.Category
import com.example.restaurantappprojektandroid.data.model.Meal
import com.example.restaurantappprojektandroid.ui.MainViewModel
import com.example.restuarantprojektapp.R
import com.example.restuarantprojektapp.databinding.MittagstischItemBinding

class MittagstischAdapter(
    private val dataset: List<Meal>,
    private val viewModel: MainViewModel
) : RecyclerView.Adapter<MittagstischAdapter.MittagstischViewHolder>() {
    inner class MittagstischViewHolder(val binding: MittagstischItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MittagstischViewHolder {

        val vb = MittagstischItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MittagstischViewHolder(vb)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: MittagstischViewHolder, position: Int) {
        val meal = dataset[position]


        holder.binding.itemPic.load(meal.mealImg)
        holder.binding.itemName.text = meal.mealName
        holder.binding.itemPrice.text = meal.price.toString() + "€"

        holder.itemView.setOnClickListener {

            viewModel.setSelectedMealId(meal.idMeal)

            holder.itemView.findNavController().navigate(R.id.mealDetailFragment)

        }


    }
}