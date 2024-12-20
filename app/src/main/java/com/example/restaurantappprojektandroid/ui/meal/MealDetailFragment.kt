package com.example.restaurantappprojektandroid.ui.meal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import coil.load
import com.example.restaurantappprojektandroid.data.datasource.AllergenDatasource
import com.example.restaurantappprojektandroid.data.model.Meal
import com.example.restaurantappprojektandroid.ui.MainViewModel
import com.example.restuarantprojektapp.R
import com.example.restuarantprojektapp.databinding.FragmentMealDetailBinding

class MealDetailFragment : Fragment() {
    private lateinit var vb: FragmentMealDetailBinding
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        vb = FragmentMealDetailBinding.inflate(inflater, container, false)
        return vb.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //falls der user anonym ist, dann soll das Herz nicht sichtbar sein
        if (viewModel.currentUser.value?.isAnonymous == true) {

            vb.ivHearth.visibility = View.INVISIBLE
        }
        viewModel.selectedMeal.observe(viewLifecycleOwner) { meal: Meal ->
            vb.ivHearth.load(if (meal.liked) R.drawable.save else R.drawable.heart)
            vb.ivMealDetail.load(meal.mealImg)
            vb.tvMealDetailTitle.text = meal.mealName
            vb.tvPreisDetail.text = meal.priceasString

            vb.ivHearth.setOnClickListener {
                meal.liked = !meal.liked
                viewModel.addToFavorites(meal)
                vb.ivHearth.load(if (meal.liked) R.drawable.save else R.drawable.heart)
            }
        }
        vb.tvAllergenen.text = AllergenDatasource().loadAllergene().toString()
        vb.ivArrowBack.setOnClickListener {
            findNavController().navigateUp()
        }
        vb.btnAllergenen.setOnClickListener {
            findNavController().navigate(R.id.allergenenFragment)
        }
    }
}
