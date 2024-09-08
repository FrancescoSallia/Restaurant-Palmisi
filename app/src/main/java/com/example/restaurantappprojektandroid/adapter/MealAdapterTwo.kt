package com.example.restaurantappprojektandroid.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.restaurantappprojektandroid.model.Meal
import com.example.restaurantappprojektandroid.ui.MainViewModel
import com.example.restuarantprojektapp.R
import com.example.restuarantprojektapp.databinding.MealmenuItemBinding

class MealAdapterTwo(

    val dataset: List<Meal>,
    val viewModel: MainViewModel

): RecyclerView.Adapter<MealAdapterTwo.MealMenuViewholder>() {
    inner class MealMenuViewholder(val binding: MealmenuItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealMenuViewholder {
        val vb: MealmenuItemBinding = MealmenuItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MealMenuViewholder(vb)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: MealMenuViewholder, position: Int) {

        val meal = dataset[position]

        var isLiked = viewModel.likedMealIds.value?.contains(meal.idMeal) ?: false
        holder.binding.likeButton.load(if (isLiked) R.drawable.save else R.drawable.heart)
        holder.binding.itemName.text = meal.mealName
        holder.binding.itemImage.load(meal.mealImg)
        holder.binding.itemPrice.text = meal.price.toString() + "€"
        holder.binding.likeButton.setOnClickListener {
            if (isLiked) {
                viewModel.removeFromFavorites(meal)
            } else {
                viewModel.addToFavorites(meal)

            }
            isLiked = !isLiked
            holder.binding.likeButton.load(if (isLiked) R.drawable.save else R.drawable.heart)
        }

        holder.itemView.setOnClickListener {

            // das soll die position speichern um wieder in der selben position zu sein nachdem man zurück navigiert, funktioniert aber noch nicht
            viewModel.recyclerViewPosition = position

            viewModel.setSelectedMealId(meal.idMeal)

            val navController = holder.itemView.findNavController()
            navController.navigate(R.id.mealDetailFragment)

        }
    }


}
