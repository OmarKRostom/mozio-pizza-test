package com.mozio.omarkrostom.arch

import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import com.mozio.omarkrostom.R

open class BaseActivity(layoutId: Int): AppCompatActivity(layoutId) {
    protected val navController by lazy {
        Navigation.findNavController(this, R.id.navMain)
    }
}