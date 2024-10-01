package com.example.restaurantappprojektandroid.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar.LayoutParams
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.restaurantappprojektandroid.MainActivity
import com.example.restaurantappprojektandroid.ui.MainViewModel
import com.example.restaurantappprojektandroid.ui.adapter.FavoriteAdapter
import com.example.restaurantappprojektandroid.ui.adapter.ReservationAdapter
import com.example.restuarantprojektapp.databinding.FragmentAllReservationListBinding

class AllReservationsAndFavoritesListFragment : Fragment() {
    private lateinit var vb: FragmentAllReservationListBinding
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel.getDataUser()
        vb = FragmentAllReservationListBinding.inflate(inflater, container, false)
        return vb.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.reservationsList.observe(viewLifecycleOwner) {
            vb.rvAllReservationList.adapter =
                ReservationAdapter(it.reversed(), viewModel, requireContext())
        }
        vb.ivArrowBack.setOnClickListener {
            (requireActivity() as MainActivity).bottomNavigation.visibility = View.VISIBLE
            findNavController().navigateUp()
        }
        vb.btnReservationList.setOnClickListener {

            setupReservationList()
        }

        vb.btnFavoritesList.setOnClickListener {
            setupFavoritesList()
        }
    }
    // Anpassung der layouts zu LinearLayout
    private fun setupReservationList() {
        vb.rvAllReservationList.layoutParams.width = LayoutParams.WRAP_CONTENT
        vb.rvAllReservationList.layoutManager = LinearLayoutManager(requireContext())
        viewModel.reservationsList.observe(viewLifecycleOwner) {
            vb.rvAllReservationList.adapter =
                ReservationAdapter(it.reversed(), viewModel, requireContext())
        }
    }
    // Anpassung der layouts zu GridLayout
    private fun setupFavoritesList() {
        vb.rvAllReservationList.layoutParams.width = 1070
        vb.rvAllReservationList.layoutManager = GridLayoutManager(requireContext(), 2)
        viewModel.likedMeals.observe(viewLifecycleOwner) {
            vb.rvAllReservationList.adapter = FavoriteAdapter(it.reversed(), viewModel)
        }
    }
}