package com.example.restaurantappprojektandroid.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.restaurantappprojektandroid.data.model.Meal
import com.example.restaurantappprojektandroid.ui.MainViewModel
import com.example.restuarantprojektapp.databinding.FavoritedItemBinding

class FavoriteAdapter(
    private val dataset: List<Meal>,
    private val viewModel: MainViewModel
) : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {
    inner class FavoriteViewHolder(val binding: FavoritedItemBinding) :RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
       var vb = FavoritedItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(vb)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {

        val meal = dataset[position]


        Log.d("TAG", "onBindViewHolder: ${viewModel.likedMeals.value}")
        holder.binding.tvFavoriteName.text = meal.mealName
        holder.binding.tvFavoritePrice.text = meal.price.toString()+ "€"
        holder.binding.ivFavoriteProfil.load(meal.mealImg)


}
}


