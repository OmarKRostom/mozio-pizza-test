package com.mozio.omarkrostom.pizza_menu.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RadioButton
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import com.mozio.omarkrostom.R
import com.mozio.omarkrostom.arch.BaseFragment
import com.mozio.omarkrostom.arch.extensions.nonNullObserve
import com.mozio.omarkrostom.databinding.FragmentPizzaMenuBinding
import com.mozio.omarkrostom.pizza_menu.viewModel.PizzaViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PizzaMenuFragment : BaseFragment() {

    private val pizzaViewModel by activityViewModels<PizzaViewModel>()

    private var _binding: FragmentPizzaMenuBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPizzaMenuBinding.inflate(inflater, container, false)
        _binding?.viewModel = pizzaViewModel
        _binding?.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pizzaViewModel.getPizzaMenu()
        setOnPizzaStyleListener()
        setOnPizzaChooseSelection()
        handlePlaceOrder()
        observeViewModels()
    }

    private fun handlePlaceOrder() {
        binding.btnSubmitOrder.setOnClickListener {
            navController.navigate(R.id.action_pizzaMenuFragment_to_orderSuccessFragment)
        }
    }

    private fun setOnPizzaChooseSelection() {
        binding.rgPizzaFlavor.setOnCheckedChangeListener { _, id ->
            pizzaViewModel.addPizza(requireActivity().findViewById<RadioButton>(id).text.toString())
        }

        binding.rgSecondPizzaFlavor.setOnCheckedChangeListener { _, id ->
            pizzaViewModel.addPizza(
                requireActivity().findViewById<RadioButton>(
                    binding.rgPizzaFlavor
                        .checkedRadioButtonId
                ).text.toString(),
                requireActivity().findViewById<RadioButton>(id).text.toString()
            )
        }
    }

    private fun setOnPizzaStyleListener() {
        binding.rgPizzaStyle.setOnCheckedChangeListener { _, id ->
            binding.grpPizzaFlavor.isVisible = id == R.id.rbHalfHalf
        }
    }

    private fun observeViewModels() {
        pizzaViewModel.pizzaOrder.nonNullObserve(this) {
            binding.tvOrderTotal.text = getString(R.string.order_total, it.price)
        }

        pizzaViewModel.pizzaListSuccess.nonNullObserve(this) {
            it.getContentIfNotHandled()?.let {
                it.forEach { pizza ->
                    val newPizzaOption = RadioButton(requireContext())
                    newPizzaOption.text = pizza.name
                    newPizzaOption.layoutParams = LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
                    )

                    binding.rgPizzaFlavor.addView(newPizzaOption)

                    val newPizzaSecondOption = RadioButton(requireContext())
                    newPizzaSecondOption.text = pizza.name
                    newPizzaSecondOption.layoutParams = LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                    binding.rgSecondPizzaFlavor.addView(newPizzaSecondOption)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}