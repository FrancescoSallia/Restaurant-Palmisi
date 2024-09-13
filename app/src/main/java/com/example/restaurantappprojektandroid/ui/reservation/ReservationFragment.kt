package com.example.restaurantappprojektandroid.ui.reservation

import android.app.AlertDialog
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.example.restaurantappprojektandroid.ui.adapter.PersonenanzahlAdapter
import com.example.restaurantappprojektandroid.ui.adapter.UhrzeitenVorschlägeAdapter
import com.example.restaurantappprojektandroid.ui.MainViewModel
import com.example.restuarantprojektapp.R
import com.example.restuarantprojektapp.databinding.FragmentReservationBinding
import java.util.Calendar

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

        val loadTime = viewModel.ReservationDatasources.loadTimes(12,22)


        vb.rvPersonenanzahl.adapter = PersonenanzahlAdapter(viewModel.ReservationDatasources.loadAnzahlAnGaesten(), viewModel)
        vb.rvUhrzeitenVorschlGe.adapter = UhrzeitenVorschlägeAdapter(loadTime,viewModel)

        //Kalender funktionen

        val calendar = Calendar.getInstance()
        val datum =  calendar.get(Calendar.DATE)
        val month = calendar.get(Calendar.MONTH)
        val year = calendar.get(Calendar.YEAR)






        val aktuelleZeit = System.currentTimeMillis()

        vb.calendarView.date = aktuelleZeit
        vb.calendarView.minDate = aktuelleZeit
        vb.tvDatumAktuellSelected.text = vb.calendarView.isSelected.toString()

        vb.alerView.visibility = View.INVISIBLE


        val alert = AlertDialog.Builder(requireContext())
            .setView(vb.alerView)
            .create()
        vb.btnTischReservieren.setOnClickListener {


           vb.alerView.visibility = View.VISIBLE

            vb.tvDate.text = vb.calendarView.date.toString()
            vb.tvPersons.text = viewModel.selectedPersonNumber.toString()
            vb.tvTime.text = viewModel.selectedTime.toString()

            vb.btnCancel.setOnClickListener {
                alert.dismiss()
                vb.alerView.visibility = View.INVISIBLE

            }
            vb.btnConfirm.setOnClickListener {
                alert.dismiss()
                vb.alerView.visibility = View.INVISIBLE

            }



        }

        viewModel.selectedTime.observe(viewLifecycleOwner) {
            vb.tvDatumAktuellSelected.text = it
        }
        viewModel.selectedPersonNumber.observe(viewLifecycleOwner) {
            vb.tvPersonalzahlTitle.text = it.toString()
        }

}

}