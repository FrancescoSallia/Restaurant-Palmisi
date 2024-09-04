package com.example.restaurantappprojektandroid

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.activityViewModels
import com.example.restaurantappprojektandroid.adapter.KategorieAdapter
import com.example.restaurantappprojektandroid.adapter.MealAdapter
import com.example.restaurantappprojektandroid.ui.MainViewModel
import com.example.restuarantprojektapp.databinding.FragmentSpeisekartenBinding

class SpeisekartenFragment : Fragment() {
    private lateinit var vb: FragmentSpeisekartenBinding
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        vb = FragmentSpeisekartenBinding.inflate(inflater, container, false)
        return vb.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getCategories()

        vb.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    viewModel.searchMeal(it)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    viewModel.searchMeal(it)
                }
                return true
            }
        })


        viewModel.repositoryCategory.observe(viewLifecycleOwner) {
            vb.rvKategorie.adapter = KategorieAdapter(it, viewModel)
        }

        viewModel.repositoryMeals.observe(viewLifecycleOwner) {
            vb.rvGerichtListe.adapter = MealAdapter(it, viewModel)

        }

        viewModel.repositorySearchMeal.observe(viewLifecycleOwner) {
            vb.rvGerichtListe.adapter = MealAdapter(it, viewModel)

        }
    }
}