package com.example.restaurantappprojektandroid.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.restaurantappprojektandroid.data.model.Meal
import com.example.restaurantappprojektandroid.ui.MainViewModel
import com.example.restuarantprojektapp.R
import com.example.restuarantprojektapp.databinding.MealmenuItemBinding

class MealAdapter(

    val dataset: List<Meal>,
    val viewModel: MainViewModel

): RecyclerView.Adapter<MealAdapter.MealMenuViewholder>() {
    inner class MealMenuViewholder(val binding: MealmenuItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealMenuViewholder {
        val vb: MealmenuItemBinding = MealmenuItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MealMenuViewholder(vb)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: MealMenuViewholder, position: Int) {

        //falls der eingelogte user anonym ist, dann soll der like button nicht angezeigt werden
        if (viewModel.currentUser.value?.isAnonymous == true) {

            holder.binding.likeButton.visibility = View.INVISIBLE
        }else{
            holder.binding.likeButton.visibility = View.VISIBLE

        }

            val meal = dataset[position]

            var likedMeal = viewModel.likedMeals.value?.find { it.idMeal == meal.idMeal }
            var isLiked = likedMeal != null
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

                viewModel.setSelectedMealId(meal.idMeal)

                val navController = holder.itemView.findNavController()
                navController.navigate(R.id.mealDetailFragment)

            }
        }

}
