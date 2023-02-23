package com.mozio.omarkrostom.pizza_menu.datasources

import com.mozio.internal.network.NetworkResource
import com.mozio.omarkrostom.arch.BaseDataSource
import com.mozio.omarkrostom.pizza_menu.models.Pizza
import com.mozio.omarkrostom.pizza_menu.remote.PizzaMenuApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface PizzaDataSource {
    fun getPizzaMenu(): Flow<NetworkResource<List<Pizza>>>
}

class PizzaDataSourceImpl @Inject constructor(private val pizzaMenuApi: PizzaMenuApi) :
    BaseDataSource(), PizzaDataSource {
    override fun getPizzaMenu() = fireRequest {
        pizzaMenuApi.getPizzaMenu()
    }
}