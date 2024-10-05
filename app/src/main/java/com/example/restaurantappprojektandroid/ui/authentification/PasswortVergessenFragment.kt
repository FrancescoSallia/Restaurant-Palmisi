package com.example.restaurantappprojektandroid.ui.authentification

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.restaurantappprojektandroid.ui.MainViewModel
import com.example.restuarantprojektapp.databinding.FragmentPasswortVergessenBinding

class PasswortVergessenFragment : Fragment() {
    private lateinit var vb: FragmentPasswortVergessenBinding
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        vb = FragmentPasswortVergessenBinding.inflate(inflater, container, false)
        return vb.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        vb.btnPasswortZurCksetzen.setOnClickListener {

            val email = vb.etEmail.text.toString()

            if (email.isNotEmpty())
             viewModel.resetPassword(email)

        }

        vb.ivArrowBack.setOnClickListener {
            findNavController().navigateUp()
        }


    }

}
