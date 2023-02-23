package com.mozio.omarkrostom.pizza_menu.viewModel

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.mozio.internal.network.NetworkResource
import com.mozio.omarkrostom.arch.BaseViewModel
import com.mozio.omarkrostom.arch.extensions.getFailureEvent
import com.mozio.omarkrostom.arch.extensions.getSuccessEvent
import com.mozio.omarkrostom.pizza_menu.models.Pizza
import com.mozio.omarkrostom.pizza_menu.repositories.PizzaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PizzaViewModel @Inject constructor(private val pizzaRepository: PizzaRepository) :
    BaseViewModel() {

    private val _pizzaList = MediatorLiveData<NetworkResource<List<Pizza>>>()
    val pizzaListSuccess = _pizzaList.getSuccessEvent()
    val pizzaListFailure = _pizzaList.getFailureEvent()

    private val _pizzaOrder = MutableLiveData<Pizza>()
    val pizzaOrder = Transformations.map(_pizzaOrder) { return@map it }

    fun getPizzaMenu() {
        _pizzaList.addSource(pizzaRepository.getPizzaMenu()) {
            _pizzaList.postValue(it)
        }
    }

    fun addPizza(pizza: String) {
        _pizzaOrder.postValue(pizzaListSuccess.value?.peekContent()?.first { it.name == pizza })
    }

    fun addPizza(pizzaOne: String, pizzaTwo: String) {
        val pizzaOnePrice =
            pizzaListSuccess.value?.peekContent()?.first { it.name == pizzaOne }?.price ?: 0.00
        val pizzaTwoPrice =
            pizzaListSuccess.value?.peekContent()?.first { it.name == pizzaTwo }?.price ?: 0.00

        val pizza = Pizza(
            buildPizzaName(pizzaOne, pizzaTwo),
            getPizzaTotal(pizzaOnePrice, pizzaTwoPrice)
        )

        _pizzaOrder.postValue(pizza)
    }

    /**
     * A pizza of two flavors would look like this: Mozzarella & Pepporoni
     * A pizza of three flavors would look like this: Mozzarella & Pepporoni & Vegetrian
     */
    private fun buildPizzaName(vararg flavors: String): String {
        val pizzaFlavorStringBuilder = StringBuilder()

        flavors.forEachIndexed { index, flavor ->
            if (index != flavors.size - 1) {
                pizzaFlavorStringBuilder.append("$flavor & ")
            } else {
                pizzaFlavorStringBuilder.append(flavor)
            }
        }

        return pizzaFlavorStringBuilder.toString()
    }

    /**
     * Calculates price based on number of flavors per pizza
     */
    private fun getPizzaTotal(vararg prices: Double): Double {
        var totalPrice = 0.0

        prices.forEach {
            totalPrice += it / prices.size
        }

        return totalPrice
    }

}