package com.immutable.cocktaildb.di

import com.immutable.cocktaildb.api.NetworkService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
class ApiInjectModule {

    @Provides
    fun provideNetworkService(): NetworkService = NetworkService.getService()

}