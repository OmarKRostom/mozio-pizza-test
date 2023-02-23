package com.mozio.omarkrostom

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.mozio.internal.network.NetworkResource
import com.mozio.omarkrostom.pizza_menu.models.Pizza
import com.mozio.omarkrostom.pizza_menu.repositories.PizzaRepository
import com.mozio.omarkrostom.pizza_menu.repositories.PizzaRepositoryImpl
import com.mozio.omarkrostom.pizza_menu.viewModel.PizzaViewModel
import io.mockk.every
import io.mockk.mockk
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class PizzaViewModelTest {

    private lateinit var sut: PizzaViewModel

    private val pizzaRepository: PizzaRepository = mockk<PizzaRepositoryImpl>()

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        sut = PizzaViewModel(pizzaRepository)
        every { pizzaRepository.getPizzaMenu() } returns MutableLiveData(
            NetworkResource.NetworkSuccess(
                listOf(Pizza("Mock Pizza", 10.00),
                    Pizza("Mock Pizza 2", 10.00))
            )
        )
        sut.pizzaListSuccess.observeForever {}
        sut.getPizzaMenu()
    }

    @Test
    fun `verify adding pizza to order works`() {
        sut.addPizza("Mock Pizza")

        sut.pizzaOrder.observeForever {}

        assert(sut.pizzaOrder.value != null)
    }

    @Test
    fun `verify adding two pizzas total order calculation works`() {
        sut.addPizza("Mock Pizza", "Mock Pizza 2")

        sut.pizzaOrder.observeForever {}

        assert(sut.pizzaOrder.value?.price == 10.0)
        assert(sut.pizzaOrder.value?.price != 30.0)
    }
}