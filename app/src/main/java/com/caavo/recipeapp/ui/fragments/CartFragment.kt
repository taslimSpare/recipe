package com.caavo.recipeapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.caavo.recipeapp.R
import com.caavo.recipeapp.databinding.FragmentCartBinding
import com.caavo.recipeapp.ui.adapters.MarketAdapter
import com.caavo.recipeapp.viewmodels.AuthViewModel
import com.caavo.recipeapp.viewmodels.MainViewModel


class CartFragment : Fragment() {

    private lateinit var binding: FragmentCartBinding
    private lateinit var viewmodel: MainViewModel
    private lateinit var navController: NavController
    private val adapter by lazy { MarketAdapter(requireContext(), viewmodel.cartItems) { recipe ->
        viewmodel.cartItems.remove(recipe)
    } }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cart, container, false)
        viewmodel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        navController = Navigation.findNavController(container!!)

        setupViews()
        setonClickListeners()

        return binding.root

    }


    private fun setupViews() {

        binding.rvCartRecipes.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        binding.rvCartRecipes.adapter = adapter

        if(viewmodel.cartItems.isEmpty()) {
            binding.tvError.visibility = View.VISIBLE
            binding.btnGoToMyItems.visibility = View.GONE
        }

    }


    private fun setonClickListeners() {

        with(binding) {

            btnGoToMyItems.setOnClickListener { navController.navigate(R.id.action_cartFragment_to_myItemsFragment) }
            ibBack.setOnClickListener {

                if(viewmodel.cartItems.isEmpty()) {
                    Toast.makeText(requireContext(), getString(R.string.no_items_in_cart), Toast.LENGTH_LONG).show()

                }
                else {
                    viewmodel.buyRecipes(viewmodel.cartItems)
                    Toast.makeText(requireContext(), getString(R.string.purchase_successful), Toast.LENGTH_LONG).show()
                    navController.navigate(R.id.action_cartFragment_to_marketFragment) }
                }
        }
    }



}