package com.example.restaurantappprojektandroid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.restuarantprojektapp.R
import com.example.restuarantprojektapp.databinding.FragmentLogoBinding
import android.os.Handler
import android.os.Looper


class LogoFragment : Fragment() {
  private lateinit var vb: FragmentLogoBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        vb = FragmentLogoBinding.inflate(inflater, container, false)
        return vb.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //GIF einfügen im imgView
        Glide.with(this)
            .asGif()
            .load(R.drawable.restaurantanimationlogo)
            .into(vb.imageView2)

        //delayed -> nach dem starten der app
        Handler(Looper.getMainLooper()).postDelayed({
            findNavController().navigate(LogoFragmentDirections.actionLogoFragmentToLogInFragment())
        }, 3000)


    }
}