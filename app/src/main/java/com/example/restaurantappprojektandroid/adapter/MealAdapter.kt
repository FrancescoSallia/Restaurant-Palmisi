package com.example.restaurantappprojektandroid.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.restaurantappprojektandroid.model.Meal
import com.example.restaurantappprojektandroid.ui.MainViewModel
import com.example.restuarantprojektapp.R
import com.example.restuarantprojektapp.databinding.GerichtItemBinding

class MealAdapter(
    private val dataset: List<Meal>,
    private val viewModel: MainViewModel
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

        var heartIsFilled = true

        holder.binding.tvMealName.text = meal.mealName
        holder.binding.ivMeal.load(meal.mealImg)
        holder.binding.tvPrice.text = meal.price.toString() + "€"
        holder.binding.ivHeart.setOnClickListener {


            if (!heartIsFilled ) {
                holder.binding.ivHeart.setImageResource(R.drawable.heart)
                heartIsFilled = true
                viewModel.removeFromFavorites(meal)
        }else {
                holder.binding.ivHeart.setImageResource(R.drawable.save)
                heartIsFilled = false
                viewModel.addToFavorites(meal)
            }
        }

        holder.itemView.setOnClickListener {

            // das soll die position speichern um wieder in der selben position zu sein nachdem man zurück navigiert, funktioniert aber noch nicht
                val position = holder.adapterPosition
                viewModel.recyclerViewPosition = position

            viewModel.setSelectedMealId(meal.idMeal)

            val navController =  holder.itemView.findNavController()
            navController.navigate(R.id.mealDetailFragment)
            
        }
    }
}