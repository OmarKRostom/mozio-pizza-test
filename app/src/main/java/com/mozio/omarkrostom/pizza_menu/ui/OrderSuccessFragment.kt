package com.mozio.omarkrostom.pizza_menu.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.mozio.omarkrostom.R
import com.mozio.omarkrostom.arch.BaseFragment
import com.mozio.omarkrostom.arch.extensions.nonNullObserve
import com.mozio.omarkrostom.databinding.FragmentSuccessBinding
import com.mozio.omarkrostom.pizza_menu.viewModel.PizzaViewModel

class OrderSuccessFragment: BaseFragment() {

    private val pizzaViewModel by activityViewModels<PizzaViewModel>()

    private var _binding: FragmentSuccessBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSuccessBinding.inflate(inflater, container, false)
        _binding?.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handlePlaceAnotherOrder()
        observeViewModels()
    }

    private fun observeViewModels() {
        pizzaViewModel.pizzaOrder.nonNullObserve(this) {
            binding.tvOrderTotal.text = getString(R.string.order_total, it.price)
            binding.tvOrderName.text = it.name
        }
    }

    private fun handlePlaceAnotherOrder() {
        binding.btnMakeAnotherOrder.setOnClickListener {
            navController.navigateUp()
        }
    }

}