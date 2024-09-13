package com.example.restaurantappprojektandroid.ui.reservation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.restaurantappprojektandroid.ui.adapter.PersonenanzahlAdapter
import com.example.restaurantappprojektandroid.ui.adapter.UhrzeitenVorschlägeAdapter
import com.example.restaurantappprojektandroid.ui.MainViewModel
import com.example.restuarantprojektapp.databinding.FragmentReservationBinding

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

//        val loadUhrzeit =  viewModel.ReservationDatasources.loadUhrzeiten(12,22)
        val loadTime = viewModel.ReservationDatasources.loadTimes(12,22)


        vb.rvPersonenanzahl.adapter = PersonenanzahlAdapter(viewModel.ReservationDatasources.loadAnzahlAnGaesten(), viewModel)
        vb.rvUhrzeitenVorschlGe.adapter = UhrzeitenVorschlägeAdapter(loadTime,viewModel)

        //Kalender funktionen
        val aktuelleZeit = System.currentTimeMillis()

        vb.calendarView.date = aktuelleZeit
        vb.calendarView.minDate = aktuelleZeit
        vb.tvDatumAktuellSelected.text = vb.calendarView.isSelected.toString()
        





        viewModel.reservations.observe(viewLifecycleOwner) {


    }

}

}