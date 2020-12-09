package com.caavo.recipeapp.ui.fragments

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
import com.caavo.recipeapp.databinding.FragmentHomeBinding
import com.caavo.recipeapp.viewmodels.MainViewModel


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewmodel: MainViewModel
    private lateinit var navController: NavController


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        viewmodel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        navController = Navigation.findNavController(container!!)

        setObservers()
        setonClickListeners()

        return binding.root
    }


    private fun setObservers() {

        viewmodel.getProfileFromRoom.observe(viewLifecycleOwner, { binding.name = it[0].name })

    }

    private fun setonClickListeners() {

        with(binding) {
            clBuyNew.setOnClickListener { navController.navigate(R.id.action_homeFragment_to_marketFragment) }
            clMyCart.setOnClickListener { navController.navigate(R.id.action_homeFragment_to_cartFragment) }
            clMyRecipes.setOnClickListener { navController.navigate(R.id.action_homeFragment_to_myItemsFragment) }
        }
    }

}
