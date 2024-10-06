package com.example.restaurantappprojektandroid.ui.authentification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.restaurantappprojektandroid.ui.MainViewModel
import com.example.restuarantprojektapp.databinding.FragmentLogInBinding

class LogInFragment : Fragment() {
    private lateinit var vb: FragmentLogInBinding
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        vb = FragmentLogInBinding.inflate(inflater, container, false)
        return vb.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.currentUser.observe(viewLifecycleOwner) {
            if (it != null) {
                findNavController().navigate(LogInFragmentDirections.actionLogInFragmentToHomeFragment())
            }
        }
        vb.tvPasswortVergessen.setOnClickListener {
            findNavController().navigate(LogInFragmentDirections.actionLogInFragmentToPasswortVergessenFragment())
        }
        vb.btnLogIn.setOnClickListener {
            val email = vb.etBenutzername.text.toString()
            val password = vb.etPasswort.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                viewModel.logIn(email, password,
                    onSuccess = {
                        successToast()
                    },
                    onFailure = {
                        failureToast()
                    }
                )
            } else {
                Toast.makeText(requireActivity(), "Füll die Felder aus", Toast.LENGTH_SHORT).show()
            }
        }
        vb.btnRegistrieren.setOnClickListener {
            findNavController()
                .navigate(
                    LogInFragmentDirections
                        .actionLogInFragmentToRegistrierenFragment()
                )
        }
        vb.btnContinueAsGast.setOnClickListener {
            viewModel.continueAsGuest()
        }
    }

    fun failureToast() {
        Toast.makeText(
            context,
            "Deine angegebenen daten sind falsch, oder du musst dich Registrieren.",
            Toast.LENGTH_SHORT
        ).show()
    }

    fun successToast() {
        Toast.makeText(
            context,
            "Erfolgreich eingeloggt",
            Toast.LENGTH_SHORT
        ).show()
    }
}