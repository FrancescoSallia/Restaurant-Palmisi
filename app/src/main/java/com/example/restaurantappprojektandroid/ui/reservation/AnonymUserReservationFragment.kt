package com.example.restaurantappprojektandroid.ui.reservation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.restaurantappprojektandroid.ui.MainViewModel
import com.example.restuarantprojektapp.R
import com.example.restuarantprojektapp.databinding.FragmentAnonymUserReservationBinding

class AnonymUserReservationFragment : Fragment() {
    private lateinit var vb: FragmentAnonymUserReservationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        vb = FragmentAnonymUserReservationBinding.inflate(inflater, container, false)
        return vb.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vb.button.setOnClickListener {
            findNavController().navigate(R.id.registrierenFragment)
        }
    }
}