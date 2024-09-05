package com.example.restaurantappprojektandroid

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.restaurantappprojektandroid.ui.MainViewModel
import com.example.restuarantprojektapp.R
import com.example.restuarantprojektapp.databinding.FragmentLogInBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


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
//
//        with(vb){
//            animationView.setAnimation(R.raw.animation)
//            animationView.playAnimation()
//            animationView.loop(true)
//        }

        Glide.with(this)
            .asGif()
            .load(R.raw.animation)
            .into(vb.animationView)

        vb.btnLogIn.setOnClickListener {

            if (vb.etBenutzername.text.toString().isNotEmpty() && vb.etPasswort.text.toString().isNotEmpty()) {
                viewModel.logIn(vb.etBenutzername.text.toString(), vb.etPasswort.text.toString())
                findNavController().navigate(LogInFragmentDirections.actionLogInFragmentToHomeFragment())
            }else{
                Toast.makeText(requireActivity(),"Füll die Felder aus", Toast.LENGTH_SHORT).show()
            }


        }

        vb.btnRegistrieren.setOnClickListener {
            val navController = findNavController()
            navController.navigate(LogInFragmentDirections.actionLogInFragmentToRegistrierenFragment())
        }

        vb.btnContinueAsGast.setOnClickListener {
            viewModel.continueAsGuest()
            val navController = findNavController()
            navController.navigate(LogInFragmentDirections.actionLogInFragmentToHomeFragment())


        }






        }
    }
