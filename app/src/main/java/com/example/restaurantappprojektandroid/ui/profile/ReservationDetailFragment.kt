package com.example.restaurantappprojektandroid.ui.profile

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.restaurantappprojektandroid.ui.MainViewModel
import com.example.restuarantprojektapp.R
import com.example.restuarantprojektapp.databinding.FragmentReservationDetailBinding

class ReservationDetailFragment : Fragment() {
    private lateinit var vb: FragmentReservationDetailBinding
    private val viewModel: MainViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        vb = FragmentReservationDetailBinding.inflate(inflater, container, false)
        return vb.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel.reservation.observe(viewLifecycleOwner) {

            val vorname = viewModel.userData.value?.vorname
            val nachname = viewModel.userData.value?.nachname

            vb.tvNameReservationDetail.text = "$vorname $nachname"
            vb.ivReservationPictureDetail.setImageResource(it.imgId)
            vb.tvReservierungsIdDetail.text = it.reservationId
            vb.tvGaestReservationDetail.text = it.gaeste.toString()
            vb.tvUhrzeitReservationDetail.text = it.datum

            //den value vom kommentarGast raus nehmen und anzeigen lassen!!
            var  bemerkung = it.kommentarGast
            if (vb.etBemerkungDetailText.hint.isNullOrEmpty()){

                vb.etBemerkungDetailText.hint = "Schreib hier dein Text..."

            }else{
                vb.etBemerkungDetailText.hint = bemerkung


            }

        }
    }

}