package com.caavo.recipeapp.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.caavo.recipeapp.R
import com.caavo.recipeapp.viewmodels.AuthViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class AuthActivity : AppCompatActivity() {

    private val authViewModel by viewModel<AuthViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
    }
}