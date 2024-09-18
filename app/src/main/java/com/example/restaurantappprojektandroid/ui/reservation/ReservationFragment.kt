package com.example.restaurantappprojektandroid.ui.reservation

import android.app.AlertDialog
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.restaurantappprojektandroid.data.datasource.ReservationDatasource
import com.example.restaurantappprojektandroid.data.model.Reservation
import com.example.restaurantappprojektandroid.ui.MainViewModel
import com.example.restaurantappprojektandroid.ui.adapter.PersonenanzahlAdapter
import com.example.restaurantappprojektandroid.ui.adapter.UhrzeitenVorschlägeAdapter
import com.example.restuarantprojektapp.R
import com.example.restuarantprojektapp.databinding.FragmentReservationBinding
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class ReservationFragment : Fragment() {

    private lateinit var vb: FragmentReservationBinding
    private val viewModel: MainViewModel by activityViewModels()

    private var zeit: String = ""
    private var personenNumber: Int = 0
    private lateinit var currentDate: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        vb = FragmentReservationBinding.inflate(inflater, container, false)
        viewModel.getDataUser()
        return vb.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.currentUser.observe(viewLifecycleOwner) {
            if(it?.isAnonymous == true) {
                //findNavController().navigate(R.id.logoFragment)
            } else {
                registeredUserView()
            }
        }
    }

    fun registeredUserView() {
        val loadTime = viewModel.ReservationDatasources.loadTimes(12, 22)
        val currentTime = LocalDateTime.now()
        val aktuelleZeit = System.currentTimeMillis()
        vb.calendarView.date = aktuelleZeit
        vb.calendarView.minDate = aktuelleZeit
        currentDate = currentTime.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
        vb.reservationTitleDatum.text = currentDate

        vb.rvPersonenanzahl.adapter =
            PersonenanzahlAdapter(viewModel.ReservationDatasources.loadAnzahlAnGaesten(), viewModel)
        vb.rvUhrzeitenVorschlGe.adapter = UhrzeitenVorschlägeAdapter(loadTime, viewModel)

        initializeObserver()
        initializeOnClickListener()
    }

    fun initializeObserver() {
        viewModel.selectedTime.observe(viewLifecycleOwner) {
            vb.tvTimeAktuellSelected.text = it
            zeit = it
        }
        viewModel.selectedPersonNumber.observe(viewLifecycleOwner) {
            vb.tvPersonalzahlTitle.text = it.toString()
            personenNumber = it
        }
        vb.calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            currentDate = "$dayOfMonth.$month.$year"
            vb.reservationTitleDatum.text = currentDate
        }
    }

    fun initializeOnClickListener() {
        vb.btnTischReservieren.setOnClickListener {

            if (personenNumber != 0 && zeit != "") {
                val builder = AlertDialog.Builder(requireContext())
                builder.setTitle("Tisch Reservierung bestätigen")
                builder.setMessage(
                    """
            Datum: $currentDate
            Personen: $personenNumber 
            Wann: $zeit
            
            Klicken sie auf OK um die Reservierung zu bestätigen
                """
                )
                builder.setPositiveButton("OK") { dialog, _ ->

                    var newTime = "$currentDate    $zeit"
                    var newReservation =  Reservation("",newTime,personenNumber,"", ReservationDatasource().loadRandomPictures().random())
                    viewModel.postReservation(newReservation)
                    dialog.dismiss()

                }
                builder.setNegativeButton("Abbrechen") { dialog, _ ->
                    dialog.dismiss()
                }
                builder.show()

            }

        }
    }

}