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
import com.example.restaurantappprojektandroid.data.model.User
import com.example.restaurantappprojektandroid.ui.MainViewModel
import com.example.restuarantprojektapp.databinding.FragmentProiflSettingsBinding

class ProiflSettingsFragment : Fragment() {
    private lateinit var vb: FragmentProiflSettingsBinding
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        vb = FragmentProiflSettingsBinding.inflate(inflater, container, false)
        return vb.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getDataUser()

        vb.btnSave.setOnClickListener {
            if (vb.etBenutzernameSettings.text.toString()
                    .isNotEmpty() || vb.etPasswordSettings.text.toString().isNotEmpty()
            ) {

                val vorname = vb.etBenutzernameSettings.text.toString()
                val nachname = vb.etPasswordSettings.text.toString()

                viewModel.updateUser(vorname, nachname)
                findNavController().navigate(ProiflSettingsFragmentDirections.actionProiflSettingsFragmentToProfilFragment())
            }

        }

//        viewModel.userRef?.addSnapshotListener { snapshot, error ->
//            if (snapshot != null && snapshot.exists()) {
//                val user = snapshot.toObject(User::class.java)
//
//                vb.etBenutzernameSettings.setText(user?.vorname)
//                vb.etPasswordSettings.setText(user?.nachname)
//
//            }
//        }

        viewModel.userData.observe(viewLifecycleOwner){
            Log.d("ProfilSettings", "userData: $it")
            vb.etBenutzernameSettings.setText(it?.vorname)
            vb.etPasswordSettings.setText(it?.nachname)

        }

        viewModel.currentUser.observe(viewLifecycleOwner) {
            if (it == null) {
                (requireActivity() as MainActivity).bottomNavigation.visibility = View.INVISIBLE
                findNavController().navigate(ProiflSettingsFragmentDirections.actionProiflSettingsFragmentToLogInFragment())
            }
        }
        vb.btnKontolSchen.setOnClickListener {
            viewModel.deleteUser()
        }

    }
}

