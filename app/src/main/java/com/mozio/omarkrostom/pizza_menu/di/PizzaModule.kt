package com.mozio.omarkrostom.pizza_menu.di

import com.mozio.internal.network.NetworkManager
import com.mozio.omarkrostom.pizza_menu.datasources.PizzaDataSource
import com.mozio.omarkrostom.pizza_menu.datasources.PizzaDataSourceImpl
import com.mozio.omarkrostom.pizza_menu.remote.PizzaMenuApi
import com.mozio.omarkrostom.pizza_menu.repositories.PizzaRepository
import com.mozio.omarkrostom.pizza_menu.repositories.PizzaRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(FragmentComponent::class, ViewModelComponent::class)
class PizzaModule {

    @Provides
    fun provideRemoteApi(networkManager: NetworkManager) =
        networkManager.create(PizzaMenuApi::class.java)

    @Provides
    fun provideDataSource(remoteApi: PizzaMenuApi): PizzaDataSource = PizzaDataSourceImpl(remoteApi)

    @Provides
    fun provideRepository(dataSource: PizzaDataSource): PizzaRepository =
        PizzaRepositoryImpl(dataSource)

}