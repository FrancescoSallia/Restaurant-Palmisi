package com.example.restaurantappprojektandroid

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.restuarantprojektapp.R
import com.example.restuarantprojektapp.databinding.FragmentLogInBinding

class LogInFragment : Fragment() {
    private lateinit var vb: FragmentLogInBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        vb = FragmentLogInBinding.inflate(inflater, container, false)
        return vb.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Glide.with(this)
            .asGif()
            .load(R.drawable.login_animation)
            .into(vb.imageView8)

    }
}