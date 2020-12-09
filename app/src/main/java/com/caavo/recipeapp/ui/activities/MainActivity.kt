package com.caavo.recipeapp.ui.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.navigation.findNavController
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.caavo.recipeapp.R
import com.caavo.recipeapp.models.DrawerItem
import com.caavo.recipeapp.viewmodels.MainViewModel
import com.caavo.recipeapp.ui.adapters.DrawerAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.drawer_content.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var navController: NavController
    private val mainViewModel by viewModel<MainViewModel>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupViews()
        observe()

    }


    private fun setupViews() {

        val drawerItems = mutableListOf<DrawerItem>().apply {
            add(DrawerItem(R.drawable.ic_home, getString(R.string.menu_home), true))
            add(DrawerItem(R.drawable.ic_cart, getString(R.string.menu_cart), false))
            add(DrawerItem(R.drawable.ic_my_items, getString(R.string.menu_my_items), false))
            add(DrawerItem(R.drawable.ic_rate_us, getString(R.string.menu_rate_us), false))
            add(DrawerItem(R.drawable.ic_share_app, getString(R.string.menu_share_app), false))
        }

        val drawerAdapter by lazy { DrawerAdapter(this, drawerItems) {item ->
            drawer_layout.closeDrawers()

            when(item.icon) {
                R.drawable.ic_home -> navController.navigate(R.id.homeFragment)
                R.drawable.ic_cart -> navController.navigate(R.id.cartFragment)
                R.drawable.ic_my_items -> navController.navigate(R.id.myItemsFragment)
                R.drawable.ic_rate_us -> { }
                R.drawable.ic_share_app -> { }
            }
        } }


        navController = findNavController( R.id.nav_host_fragment)

        toggle =
            ActionBarDrawerToggle(this, drawer_layout, R.string.app_name, R.string.app_name)
        toggle.syncState()

        rvDrawer.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvDrawer.adapter = drawerAdapter


        btn_log_in.setOnClickListener { mainViewModel.logout(); drawer_layout.closeDrawers(); Toast.makeText(this, "Logged out", Toast.LENGTH_LONG).show() }

    }


    private fun observe() {

        mainViewModel.getProfileFromRoom.observe(this, {
            tvUsernameHomePage.text = it[0].name
        })
    }


}