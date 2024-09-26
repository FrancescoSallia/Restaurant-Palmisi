package com.example.restaurantappprojektandroid.ui.profile

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import coil.load
import com.example.restaurantappprojektandroid.MainActivity
import com.example.restaurantappprojektandroid.ui.MainViewModel
import com.example.restuarantprojektapp.databinding.FragmentProiflSettingsBinding

class ProiflSettingsFragment : Fragment() {
    private lateinit var vb: FragmentProiflSettingsBinding
    private val viewModel: MainViewModel by activityViewModels()
    private var profilBild: Uri? = null


    // diese funktion ist für die Bildauswahl zuständig (Profilbild)
    private val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let { uri->
            Log.d("ProfilSettings", "let funktion: $uri")


            profilBild = uri
            viewModel.uploadImage(profilBild!!)

//            viewModel.userData.observe(viewLifecycleOwner){ user ->
//
//                profilBild = user.profilbild?.toUri()
//
//
//            }

            viewModel.userData.observe(viewLifecycleOwner) {

                Log.d("ProfilSettings", "observe: $profilBild")

            }
            vb.ivProfilPic.setImageURI(profilBild)

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel.getDataUser()
        vb = FragmentProiflSettingsBinding.inflate(inflater, container, false)
        return vb.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.profilPicture.observe(viewLifecycleOwner) {
            if (it != null) {
                vb.ivProfilPic.setImageURI(Uri.parse(it.toString()))
                Log.d("ProfilSettings", "userData: ${it}")
                profilBild = it.toUri()
                Log.d("ProfilSettings", "uri profilbild: ${it}")

            }
        }

        // funktion um die Bildauswahl zu starten
        fun openGallery() {
             getContent.launch("image/*")

        }

        vb.btnFloatingCamera.setOnClickListener {

            openGallery()
        }

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

        viewModel.userData.observe(viewLifecycleOwner){
            Log.d("ProfilSettings", "userData: $it")
            vb.etBenutzernameSettings.setText(it?.vorname)
            vb.etPasswordSettings.setText(it?.nachname)
            vb.ivProfilPic.load(it.profilePicture)

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

