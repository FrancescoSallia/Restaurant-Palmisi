package com.example.restaurantappprojektandroid.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.restaurantappprojektandroid.model.Meal
import com.example.restuarantprojektapp.R
import com.example.restuarantprojektapp.databinding.GerichtItemBinding

class MealAdapter(
    private val dataset: List<Meal>
):RecyclerView.Adapter<MealAdapter.MealViewHolder>() {
   inner class MealViewHolder(val binding:GerichtItemBinding):RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        val binding = GerichtItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return MealViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        val meal = dataset[position]

        holder.binding.tvMealName.text = meal.mealName
        holder.binding.ivMeal.load(meal.mealImg)

        holder.itemView.setOnClickListener {

           var navController =  holder.itemView.findNavController()
            navController.navigate(R.id.mealDetailFragment)
        }
    }
}