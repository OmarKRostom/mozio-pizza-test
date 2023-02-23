package com.mozio.omarkrostom.pizza_menu.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.mozio.internal.network.NetworkResource
import com.mozio.omarkrostom.arch.BaseRepository
import com.mozio.omarkrostom.pizza_menu.datasources.PizzaDataSource
import com.mozio.omarkrostom.pizza_menu.models.Pizza
import javax.inject.Inject

interface PizzaRepository {
    fun getPizzaMenu(): LiveData<NetworkResource<List<Pizza>>>
}

class PizzaRepositoryImpl @Inject constructor(private val pizzaDataSource: PizzaDataSource) :
    BaseRepository(), PizzaRepository {
    override fun getPizzaMenu(): LiveData<NetworkResource<List<Pizza>>> =
        pizzaDataSource.getPizzaMenu().asLiveData()
}