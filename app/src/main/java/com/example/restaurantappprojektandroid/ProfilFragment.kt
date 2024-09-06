package com.example.restaurantappprojektandroid

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.restaurantappprojektandroid.adapter.FavoriteAdapter
import com.example.restaurantappprojektandroid.ui.MainViewModel
import com.example.restuarantprojektapp.R
import com.example.restuarantprojektapp.databinding.FragmentProfilBinding

class ProfilFragment : Fragment() {
    private lateinit var vb: FragmentProfilBinding
    private val viewModel: MainViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        vb = FragmentProfilBinding.inflate(inflater, container, false)
        return vb.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel.likedMeals.observe(viewLifecycleOwner) {

            vb.rvFavorite.adapter = FavoriteAdapter(it,viewModel)

        }

        vb.btnAusloggen.setOnClickListener {
            viewModel.logOut()
            (requireActivity() as MainActivity).bottomNavigation.visibility = View.INVISIBLE
            findNavController().navigate(ProfilFragmentDirections.actionProfilFragmentToLogInFragment())
        }

        vb.btnProfilSetting.setOnClickListener {
            findNavController().navigate(ProfilFragmentDirections.actionProfilFragmentToProiflSettingsFragment())
        }

    }
}