package com.mozio.omarkrostom.pizza_menu.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mozio.omarkrostom.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PizzaMenuContainerActivity: AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}