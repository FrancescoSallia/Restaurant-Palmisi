package com.example.restaurantappprojektandroid.ui.meal

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import coil.load
import com.example.restaurantappprojektandroid.data.model.Meal
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

        //falls der user anonym ist, dann soll das Herz nicht sichtbar sein
        if (viewModel.currentUser.value?.isAnonymous == true) {

            vb.ivHearth.visibility = View.INVISIBLE
        }

        viewModel.likedMeals.observe(viewLifecycleOwner) { userLikedMeals ->
            var findMeal = userLikedMeals.find { it.idMeal == viewModel.selectedMealID}

            vb.ivHearth.load(if (findMeal != null) R.drawable.save else R.drawable.heart)

        }

        viewModel.repositoryMealDetail.observe(viewLifecycleOwner) { meals: List<Meal> ->
            val meal = meals.first()


            vb.ivMealDetail.load(meal.mealImg)
            vb.tvMealDetailTitle.text = meal.mealName
            vb.tvPreisDetail.text = meal.priceasString

            vb.ivHearth.setOnClickListener {
                handleFavoriteMealState(isLiked, meal)
                vb.ivHearth.load(if (isLiked) R.drawable.save else R.drawable.heart)
                //es buggt noch wenn man im gelikten Zustand im Detailfragment reingeht und auf like klickt wiederholt !!
                isLiked = !isLiked
            }
        }
    }

    private fun handleFavoriteMealState(isLikedfromUser: Boolean, meal: Meal) {
        if (isLikedfromUser) {
            viewModel.removeFromFavorites(meal)
        } else {
            viewModel.addToFavorites(meal)
        }


    }
}
