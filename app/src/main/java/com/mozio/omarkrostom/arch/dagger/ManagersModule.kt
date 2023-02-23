package com.mozio.omarkrostom.arch.dagger

import android.content.Context
import com.mozio.internal.network.NetworkManager
import com.mozio.omarkrostom.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class ManagersModule {
    @Provides
    fun providesNetworkManager(@ApplicationContext context: Context): NetworkManager =
        NetworkManager(
            context,
            BuildConfig.BASE_URL
        )
}