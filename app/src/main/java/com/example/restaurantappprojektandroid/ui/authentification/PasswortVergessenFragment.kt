package com.example.restaurantappprojektandroid.ui.authentification

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.transition.Visibility
import com.example.restaurantappprojektandroid.MainActivity
import com.example.restaurantappprojektandroid.ui.MainViewModel
import com.example.restuarantprojektapp.databinding.FragmentPasswortVergessenBinding
import com.google.firebase.Timestamp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.getValue

class PasswortVergessenFragment : Fragment() {
    private lateinit var vb: FragmentPasswortVergessenBinding
    private val viewModel: MainViewModel by activityViewModels()

    private val args: PasswortVergessenFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        vb = FragmentPasswortVergessenBinding.inflate(inflater, container, false)
        //BottomNavigationBar einblenden auf Visible
        (requireActivity() as MainActivity).bottomNavigation.visibility = INVISIBLE
        return vb.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vb.ivArrowBack.setOnClickListener {
            findNavController().navigateUp()
        }

        if(!args.userEmail.isNullOrEmpty()) {
            vb.textInputLayout7.hint = "Passwort"
            vb.etEmail.setText("")
            vb.etEmail.inputType = android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
            vb.tvConfirmDelete.text = "Bestätige mit deinem Passwort um dein Account zu Löschen"
            vb.btnPasswortZurCksetzen.text = "Bestätigen"

            vb.btnPasswortZurCksetzen.isEnabled = false

            vb.etEmail.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    vb.btnPasswortZurCksetzen.isEnabled = !s.isNullOrBlank()
                }
                override fun afterTextChanged(s: Editable?) {}
            })

            vb.btnPasswortZurCksetzen.setOnClickListener {
                val passwort = vb.etEmail.text.toString()
                val userEmail = viewModel.currentUser.value?.email

                if (userEmail != null && passwort.isNotEmpty()) {
                    viewModel.reAuthentification(
                        userEmail,
                        passwort,
                        success = {
                            viewModel.deleteUser {
                                findNavController().navigate(
                                    PasswortVergessenFragmentDirections.actionPasswortVergessenFragmentToLogInFragment()
                                )
                            }
//                            Toast.makeText(
//                                requireContext(),
//                                "Erfolgreich Re-Authentifiziert und Account gelöscht",
//                                Toast.LENGTH_SHORT
//                            )
//                                .show()
                        },
                        onFailure = { exception ->
                            Toast.makeText(
                                requireContext(),
                                "Error: ${exception.message}",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                    )

                }
            }

        } else {
            vb.btnPasswortZurCksetzen.setOnClickListener {
                val email = vb.etEmail.text.toString()
                if (email.isNotEmpty())
                    viewModel.resetPassword(email)
            }
            vb.tvConfirmDelete.text = "Gib deine E-mail adresse ein"
        }
    }
}
