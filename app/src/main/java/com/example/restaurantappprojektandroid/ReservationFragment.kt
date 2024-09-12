package com.example.restaurantappprojektandroid

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.red
import androidx.fragment.app.activityViewModels
import com.example.restaurantappprojektandroid.adapter.PersonenanzahlAdapter
import com.example.restaurantappprojektandroid.adapter.UhrzeitenVorschlägeAdapter
import com.example.restaurantappprojektandroid.ui.FirestoreRepository
import com.example.restaurantappprojektandroid.ui.MainViewModel
import com.example.restuarantprojektapp.R
import com.example.restuarantprojektapp.databinding.FragmentReservationBinding
import kotlin.time.Duration.Companion.days

class ReservationFragment : Fragment() {

    private lateinit var vb: FragmentReservationBinding
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        vb = FragmentReservationBinding.inflate(inflater, container, false)
        return vb.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vb.rvPersonenanzahl.adapter = PersonenanzahlAdapter(viewModel.ReservationDatasources.loadAnzahlAnGaesten(), viewModel)
        vb.rvUhrzeitenVorschlGe.adapter = UhrzeitenVorschlägeAdapter(viewModel.ReservationDatasources.loadUhrzeiten(),viewModel)

        //Kalender funktionen

        val aktuelleZeit = System.currentTimeMillis()

        vb.calendarView.date = aktuelleZeit
        vb.calendarView.minDate = aktuelleZeit



        viewModel.reservations.observe(viewLifecycleOwner) {


    }

}

}