package com.mozio.omarkrostom.arch

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

abstract class BaseFragment: Fragment() {
    protected val navController by lazy {
        findNavController()
    }

    open fun handleBackPressed() {
        activity?.finish()
    }
}