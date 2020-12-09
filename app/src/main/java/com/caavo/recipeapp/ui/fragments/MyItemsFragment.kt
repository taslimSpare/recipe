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
import com.caavo.recipeapp.databinding.FragmentMyItemsBinding
import com.caavo.recipeapp.models.RecipeObject
import com.caavo.recipeapp.ui.adapters.MyRecipesAdapter
import com.caavo.recipeapp.viewmodels.MainViewModel


class MyItemsFragment : Fragment() {

    private lateinit var binding: FragmentMyItemsBinding
    private lateinit var viewmodel: MainViewModel
    private lateinit var navController: NavController
    private var items = mutableListOf<RecipeObject>()
    private val adapter by lazy { MyRecipesAdapter(requireContext(), items) }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_items, container, false)
        viewmodel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        navController = Navigation.findNavController(container!!)

        setupViews()
        observe()
        setOnClickListeners()

        return binding.root

    }

    private fun setupViews() {

        binding.rvMyRecipes.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        binding.rvMyRecipes.adapter = adapter

    }

    private fun observe() {

        viewmodel.getMyRecipesFromRoom.observe(viewLifecycleOwner, {
            if(it.isEmpty()) {
                binding.tvError.visibility = View.VISIBLE
            }
            else {
                items.addAll(it)
                adapter.notifyDataSetChanged()
            }
        })

    }

    private fun setOnClickListeners() {
        with(binding) {
            ibBack.setOnClickListener { navController.navigate(R.id.action_myItemsFragment_to_cartFragment) }
        }
    }

}