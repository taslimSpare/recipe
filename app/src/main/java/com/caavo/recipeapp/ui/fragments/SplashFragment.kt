package com.caavo.recipeapp.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.caavo.recipeapp.R
import com.caavo.recipeapp.databinding.FragmentSplashBinding
import com.caavo.recipeapp.ui.activities.MainActivity
import com.caavo.recipeapp.viewmodels.AuthViewModel


class SplashFragment : Fragment() {

    private lateinit var binding: FragmentSplashBinding
    private lateinit var viewmodel: AuthViewModel
    private lateinit var navController: NavController


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_splash, container, false)
        viewmodel = ViewModelProviders.of(this).get(AuthViewModel::class.java)
        navController = Navigation.findNavController(container!!)

        viewmodel.fetchAuthStatus()

        viewmodel.getAuthStatusLiveData().observe(viewLifecycleOwner, {
            if(it) {
                startActivity(
                    Intent(
                        requireContext(),
                        MainActivity::class.java
                    )
                )
            }
            else {
                navController.navigate(R.id.action_splashFragment_to_signInFragment)
            }
        })

        return binding.root

    }

}