package com.example.restaurantappprojektandroid.ui.profile

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import coil.load
import coil.transform.CircleCropTransformation
import com.example.restaurantappprojektandroid.MainActivity
import com.example.restaurantappprojektandroid.ui.MainViewModel
import com.example.restuarantprojektapp.R
import com.example.restuarantprojektapp.databinding.FragmentProiflSettingsBinding

class ProiflSettingsFragment : Fragment() {
    private lateinit var vb: FragmentProiflSettingsBinding
    private val viewModel: MainViewModel by activityViewModels()

    private var profilBild: Uri? = null

    private val getContent =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                val savedUri = viewModel.saveImageLocally(requireContext(), it)
                profilBild = savedUri
                vb.ivProfilPic.setImageURI(savedUri)
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        vb = FragmentProiflSettingsBinding.inflate(inflater, container, false)
        return vb.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Check Login
        viewModel.getDataUser()
        viewModel.currentUser.observe(viewLifecycleOwner) {
            if (it == null) {
                (requireActivity() as MainActivity).bottomNavigation.visibility = View.INVISIBLE
                findNavController().navigate(ProiflSettingsFragmentDirections.actionProiflSettingsFragmentToLogInFragment())
            }
        }

        // User-Daten anzeigen
        viewModel.userData.observe(viewLifecycleOwner) {
            vb.etBenutzernameSettings.setText(it?.vorname)
            vb.etPasswordSettings.setText(it?.nachname)

            val profilePic = it.profilePicture
            if (profilePic.isNullOrEmpty()) {
                vb.ivProfilPic.load(R.drawable.profil) {
                    crossfade(true)
                    transformations(CircleCropTransformation())
                }
            } else {
                vb.ivProfilPic.load(profilePic) {
                    crossfade(true)
                    placeholder(R.drawable.profil)
                    transformations(CircleCropTransformation())
                }
            }
        }

        // Bild beim Start laden
        val localImageUri = viewModel.loadLocalImage(requireContext())
        localImageUri?.let {
            profilBild = it
//            vb.ivProfilPic.setImageURI(it)
            vb.ivProfilPic.load(it) {
                placeholder(R.drawable.profil)
//                transformations(CircleCropTransformation())
                crossfade(true)
            }
        }

        // Galerie öffnen
        vb.btnFloatingCamera.setOnClickListener {
            getContent.launch("image/*")
        }

        // Speichern
        vb.btnSave.setOnClickListener {
            val vorname = vb.etBenutzernameSettings.text.toString()
            val nachname = vb.etPasswordSettings.text.toString()

            if (vorname.isNotEmpty() || nachname.isNotEmpty()) {
                viewModel.updateUser(vorname, nachname, profilBild)
                findNavController().navigate(ProiflSettingsFragmentDirections.actionProiflSettingsFragmentToProfilFragment())
            }
        }

        vb.btnKontolSchen.setOnClickListener {

            findNavController().navigate(ProiflSettingsFragmentDirections.actionProiflSettingsFragmentToPasswortVergessenFragment(viewModel.currentUser.value?.email.toString()))
        }

        // Zurück
        vb.ivBackArrow.setOnClickListener {
            findNavController().navigateUp()
        }

        // Profilbild entfernen
        vb.btnProfilbildEntfernen.setOnClickListener {
            viewModel.removeLocalImage(requireContext())
            profilBild = null
            vb.ivProfilPic.setImageURI(null)
            vb.ivProfilPic.load(null) {
                placeholder(R.drawable.profil)
            }
        }
    }
}

