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
import androidx.navigation.fragment.navArgs
import androidx.viewbinding.ViewBinding
import com.example.restaurantappprojektandroid.MainActivity
import com.example.restaurantappprojektandroid.data.datasource.ReservationDatasource
import com.example.restaurantappprojektandroid.data.model.Reservation
import com.example.restaurantappprojektandroid.ui.MainViewModel
import com.example.restaurantappprojektandroid.ui.adapter.PersonenanzahlAdapter
import com.example.restaurantappprojektandroid.ui.adapter.UhrzeitenVorschlägeAdapter
import com.example.restuarantprojektapp.R
import com.example.restuarantprojektapp.databinding.FragmentAnonymUserReservationBinding
import com.example.restuarantprojektapp.databinding.FragmentReservationBinding
import com.google.android.material.navigation.NavigationBarMenu
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class ReservationFragment : Fragment() {

    private lateinit var vb: ViewBinding
    private val viewModel: MainViewModel by activityViewModels()

    private var zeit: String = ""
    private var personenNumber: Int = 0
    private lateinit var currentDate: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel.getDataUser()
        vb = if(viewModel.currentUser.value?.isAnonymous == true) {
            FragmentAnonymUserReservationBinding.inflate(layoutInflater)
        } else {
            FragmentReservationBinding.inflate(layoutInflater)
        }
        return vb.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.currentUser.observe(viewLifecycleOwner) {
            if(it?.isAnonymous == true) {
                (vb as? FragmentAnonymUserReservationBinding)?.button?.setOnClickListener {
                    findNavController().navigate(ReservationFragmentDirections
                        .actionReservationFragmentToRegistrierenFragment())
                }
            } else if(it != null){
                registeredUserView()
            }else {
                funktioniert immer noch nicht!!
//                (requireActivity() as MainActivity).bottomNavigation.visibility = View.INVISIBLE
                findNavController().popBackStack()
                findNavController().navigate(R.id.reservationFragment)
            }
        }
    }

    fun registeredUserView() {
        val binding = vb
        if (binding is FragmentReservationBinding) {
            val loadTime = viewModel.ReservationDatasources.loadTimes(
                12,
                22
            )
            val currentTime = LocalDateTime.now()
            val aktuelleZeit = System.currentTimeMillis()
            binding.calendarView.date = aktuelleZeit
            binding.calendarView.minDate = aktuelleZeit
            currentDate = currentTime.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
            binding.reservationTitleDatum.text = currentDate

            binding.rvPersonenanzahl.adapter = PersonenanzahlAdapter(
                viewModel.ReservationDatasources.loadAnzahlAnGaesten(),
                viewModel
            )
            binding.rvUhrzeitenVorschlGe.adapter = UhrzeitenVorschlägeAdapter(
                loadTime,
                viewModel
            )

            viewModel.selectedTime.observe(viewLifecycleOwner) {
                binding.tvTimeAktuellSelected.text = it
                zeit = it
            }
            viewModel.selectedPersonNumber.observe(viewLifecycleOwner) {
                binding.tvPersonalzahlTitle.text = it.toString()
                personenNumber = it
            }
            binding.calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
                currentDate = "$dayOfMonth.$month.$year"
                binding.reservationTitleDatum.text = currentDate
            }
            binding.btnTischReservieren.setOnClickListener {

                if (personenNumber != 0&&zeit != "") {
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
                        var newReservation = Reservation(
                            "",
                            newTime,
                            personenNumber,
                            "",
                            ReservationDatasource().loadRandomPictures()
                                .random()
                        )
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
}