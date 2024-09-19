package com.example.restaurantappprojektandroid.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.restaurantappprojektandroid.MainActivity
import com.example.restaurantappprojektandroid.ui.MainViewModel
import com.example.restuarantprojektapp.R
import com.example.restuarantprojektapp.databinding.FragmentAnonymUserProfilBinding

class AnonymUserProfilFragment : Fragment() {
    private lateinit var vb: FragmentAnonymUserProfilBinding
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        vb = FragmentAnonymUserProfilBinding.inflate(inflater, container, false)
        return vb.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        vb.btnAlsGastAbmelden.setOnClickListener {
            (requireActivity() as MainActivity).bottomNavigation.visibility = View.GONE
            viewModel.logOut()
        }


    }
}