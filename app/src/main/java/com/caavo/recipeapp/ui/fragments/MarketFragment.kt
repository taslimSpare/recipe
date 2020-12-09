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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.caavo.recipeapp.R
import com.caavo.recipeapp.databinding.FragmentMarketBinding
import com.caavo.recipeapp.models.RecipeObject
import com.caavo.recipeapp.models.State
import com.caavo.recipeapp.ui.adapters.MarketAdapter
import com.caavo.recipeapp.viewmodels.MainViewModel


class MarketFragment : Fragment() {

    private lateinit var binding: FragmentMarketBinding
    private lateinit var viewmodel: MainViewModel
    private lateinit var navController: NavController
    private val recipeList = arrayListOf<RecipeObject>()
    private val adapter by lazy { MarketAdapter(requireContext(), recipeList) { recipe ->
        recipe.isInCart = !recipe.isInCart
        if(recipe.isInCart) { viewmodel.cartItems.add(recipe) } else { viewmodel.cartItems.remove(recipe) }
    } }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_market, container, false)
        viewmodel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        navController = Navigation.findNavController(container!!)

        setupViews()
        setObservers()
        setonClickListeners()

        viewmodel.fetchAllRecipes()

        return binding.root

    }


    private fun setupViews() {

        binding.rvAllRecipes.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        binding.rvAllRecipes.adapter = adapter

    }


    private fun setObservers() {

        viewmodel.getMarketRecipeLiveData().observe(viewLifecycleOwner, {

            when(it.state) {

                State.LOADING -> {
                    binding.btnGoToCart.visibility = View.GONE
                    binding.pbProgressBar.visibility = View.VISIBLE
                }

                State.ERROR -> {
                    binding.pbProgressBar.visibility = View.GONE
                    binding.btnGoToCart.visibility = View.GONE
                    binding.tvError.visibility = View.VISIBLE
                }

                State.SUCCESS -> {
                    binding.pbProgressBar.visibility = View.GONE
                    binding.btnGoToCart.visibility = View.VISIBLE

                    recipeList.clear()

                    it.data?.forEach { d -> recipeList.add(d) }
                    adapter.notifyDataSetChanged()
                }
            }

        })

    }

    private fun setonClickListeners() {

        with(binding) {

            btnGoToCart.setOnClickListener { navController.navigate(R.id.action_marketFragment_to_cartFragment) }
            tvError.setOnClickListener { viewmodel.fetchAllRecipes() }
            ibBack.setOnClickListener { navController.navigate(R.id.action_marketFragment_to_homeFragment) }
        }
    }

}