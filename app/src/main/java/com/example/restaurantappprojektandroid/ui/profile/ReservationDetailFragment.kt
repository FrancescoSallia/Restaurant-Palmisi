package com.example.restaurantappprojektandroid.ui.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.restaurantappprojektandroid.MainActivity
import com.example.restaurantappprojektandroid.ui.MainViewModel
import com.example.restuarantprojektapp.databinding.FragmentReservationDetailBinding

class ReservationDetailFragment : Fragment() {
    private lateinit var vb: FragmentReservationDetailBinding
    private val viewModel: MainViewModel by activityViewModels()
    private var bemerkung = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (requireActivity()as MainActivity).bottomNavigation.visibility = View.GONE
        vb = FragmentReservationDetailBinding.inflate(inflater, container, false)
        return vb.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vb.ivArrowBack.setOnClickListener {
            (requireActivity()as MainActivity).bottomNavigation.visibility = View.VISIBLE
            findNavController().navigateUp()
        }
        viewModel.reservation.observe(viewLifecycleOwner) { reservation ->
            val vorname = viewModel.userData.value?.vorname
            val nachname = viewModel.userData.value?.nachname

            vb.tvNameReservationDetail.text = "$vorname $nachname"
            vb.ivReservationPictureDetail.setImageResource(reservation.imgId)
            vb.tvReservierungsIdDetail.text = reservation.reservationId
            vb.tvGaestReservationDetail.text = reservation.gaeste.toString()
            vb.tvUhrzeitReservationDetail.text = reservation.datum

            //den value vom kommentarGast raus nehmen und anzeigen lassen!!
            bemerkung = reservation.kommentarGast

            if (bemerkung.isNullOrEmpty()) {
                vb.etBemerkungDetailText.hint = bemerkung
            } else {
                vb.etBemerkungDetailText.setHint(bemerkung)
            }
            vb.btnTischStornieren.setOnClickListener {
                viewModel.deleteReservation(reservation.reservationId)
                findNavController().navigateUp()
            }
            vb.btnSaveReservation.setOnClickListener {
                var kommentar = vb.etBemerkungDetailText
                if (kommentar.hint.toString().isNotEmpty() && kommentar.text.toString()
                        .isNotEmpty()
                ) {
                    vb.etBemerkungDetailText.setHint(kommentar.hint.toString())
                    viewModel.updateReservation(kommentar.text.toString())
                    findNavController().navigateUp()
                } else {
                    findNavController().navigateUp()
                }
            }
        }
    }
}