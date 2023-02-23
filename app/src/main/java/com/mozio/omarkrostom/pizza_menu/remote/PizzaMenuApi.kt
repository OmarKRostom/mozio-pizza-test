package com.mozio.omarkrostom.pizza_menu.remote

import com.mozio.omarkrostom.pizza_menu.models.Pizza
import retrofit2.Response
import retrofit2.http.GET

interface PizzaMenuApi {

    @GET("pizzas.json")
    suspend fun getPizzaMenu(): Response<List<Pizza>>

}