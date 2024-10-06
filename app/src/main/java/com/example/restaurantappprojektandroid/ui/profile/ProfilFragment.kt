package com.example.restaurantappprojektandroid.ui.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import coil.load
import coil.transform.CircleCropTransformation
import com.example.restaurantappprojektandroid.MainActivity
import com.example.restaurantappprojektandroid.ui.MainViewModel
import com.example.restaurantappprojektandroid.ui.adapter.FavoriteAdapter
import com.example.restaurantappprojektandroid.ui.adapter.ReservationAdapter
import com.example.restuarantprojektapp.R
import com.example.restuarantprojektapp.databinding.FragmentAnonymUserProfilBinding
import com.example.restuarantprojektapp.databinding.FragmentProfilBinding

class ProfilFragment : Fragment() {
    private lateinit var vb: ViewBinding
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel.getDataUser()
        (requireActivity()as MainActivity).bottomNavigation.visibility = View.VISIBLE
        vb = if(viewModel.currentUser.value?.isAnonymous == true) {
            FragmentAnonymUserProfilBinding.inflate(layoutInflater)
        } else {
            FragmentProfilBinding.inflate(layoutInflater)
        }
        return vb.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.currentUser.observe(viewLifecycleOwner) {
            if (it?.isAnonymous == true) {
                (vb as? FragmentAnonymUserProfilBinding)?.btnAlsGastAbmelden?.setOnClickListener {
                    viewModel.logOut()
                }
            }else if (it != null) {
                viewModel.snapShotListenerForReservation()
                loggedUser()
            } else {
                (requireActivity() as MainActivity).bottomNavigation.visibility = View.INVISIBLE
                findNavController().popBackStack()
                findNavController().navigate(R.id.logInFragment)
            }
        }
    }
    private fun loggedUser() {
        val binding = vb
        if(binding is FragmentProfilBinding) {
            viewModel.likedMeals.observe(viewLifecycleOwner) {
                binding.rvFavorite.adapter = FavoriteAdapter(it.reversed(),viewModel)
            }
            // Diese funktion ist für das Profilbild zuständig falls ein profilbild vorhanden ist
            viewModel.userData.observe(viewLifecycleOwner) {
                binding.ivProfilPicProfil.load(it.profilePicture) {
                    crossfade(true)
                    placeholder(R.drawable.profil)
                    transformations(CircleCropTransformation())
                }
                binding.tvProfilNameTitle.text = it.vorname + " " + it.nachname
            }
            binding.btnAusloggen.setOnClickListener {
                viewModel.logOut()
            }
            binding.btnProfilSetting.setOnClickListener {
                findNavController().navigate(ProfilFragmentDirections.actionProfilFragmentToProiflSettingsFragment())
            }
            viewModel.reservationsList.observe(viewLifecycleOwner) {
                binding.rvReservationItem.adapter = ReservationAdapter(it.sortedBy { it.reservationId.reversed() }, viewModel,requireContext())
            }
            binding.tvMehrReservation.setOnClickListener {
                (requireActivity() as MainActivity).bottomNavigation.visibility = View.GONE
                findNavController().navigate(ProfilFragmentDirections.actionProfilFragmentToAllReservationListFragment())
            }
        }
    }
}