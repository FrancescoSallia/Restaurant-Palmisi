package com.example.restaurantappprojektandroid

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import coil.load
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

        viewModel.getMealById()

        vb = FragmentMealDetailBinding.inflate(inflater, container, false)
        return vb.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.repositoryMealDetail.observe(viewLifecycleOwner) {

            vb.ivMealDetail.load(it.first().mealImg)
            vb.tvMealDetailTitle.text = it.first().mealName
            vb.tvPreisDetail.text = it.first().priceasString

        }



    }
}