package com.example.restaurantappprojektandroid

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import coil.load
import com.example.restaurantappprojektandroid.model.Meal
import com.example.restaurantappprojektandroid.model.User
import com.example.restaurantappprojektandroid.ui.MainViewModel
import com.example.restuarantprojektapp.R
import com.example.restuarantprojektapp.databinding.FragmentMealDetailBinding

class MealDetailFragment : Fragment() {
    private lateinit var vb: FragmentMealDetailBinding
    private val viewModel: MainViewModel by activityViewModels()
    private var isLiked = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel.getMealById()

        vb = FragmentMealDetailBinding.inflate(inflater, container, false)
        return vb.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.likedMeals.observe(viewLifecycleOwner) { userLikedMeals ->
            Log.i("DEBUG", "likedMeals: $userLikedMeals\nselectedMealID: ${viewModel.selectedMealID}")
            isLiked = userLikedMeals.contains(viewModel.selectedMealID)
            vb.ivHearth.load(if (isLiked) R.drawable.save else R.drawable.heart)
        }

        viewModel.repositoryMealDetail.observe(viewLifecycleOwner) { meals: List<Meal> ->
            val meal = meals.first()
            vb.ivMealDetail.load(meal.mealImg)
            vb.tvMealDetailTitle.text = meal.mealName
            vb.tvPreisDetail.text = meal.priceasString
            vb.ivHearth.setOnClickListener {
                handleFavoriteMealState(isLiked, meal)
            }
        }
    }

    private fun handleFavoriteMealState(isLiked: Boolean, meal: Meal) {
        if (isLiked) {
            viewModel.removeFromFavorites(meal)
        } else {
            viewModel.addToFavorites(meal)
        }
    }
}