package com.example.restaurantappprojektandroid.ui.authentification

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.restaurantappprojektandroid.ui.MainViewModel
import com.example.restuarantprojektapp.databinding.FragmentRegistrierenBinding

class RegistrierenFragment : Fragment() {
    private lateinit var vb: FragmentRegistrierenBinding
    private val viewModel: MainViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        vb = FragmentRegistrierenBinding.inflate(inflater, container, false)
        return vb.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vb.btnRegistrierenRegistrieren.setOnClickListener {

            val vorname = vb.etVornameRegistrieren.text.toString()
            val nachname = vb.etNachnameRegistrieren.text.toString()

            val benutzername = vb.etBenutzernameRegistrieren.text.toString()
            val firstPasswort = vb.etPasswortRegistrieren.text.toString()
//            val secondPasswort = vb.etPasswortReplyRegistrieren.text.toString()

            if (benutzername.isNotEmpty() || firstPasswort.isNotEmpty() || vorname.isNotEmpty() || nachname.isNotEmpty()) {
                viewModel.registration(benutzername,firstPasswort, vorname, nachname)

                findNavController().navigate(RegistrierenFragmentDirections.actionRegistrierenFragmentToHomeFragment())
            }else{
                Toast.makeText(requireContext(),"Fülle alle Felder aus!",Toast.LENGTH_SHORT).show()
            }
    }



    }
}