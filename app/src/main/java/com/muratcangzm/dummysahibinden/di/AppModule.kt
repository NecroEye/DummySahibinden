package com.muratcangzm.dummysahibinden.di

import android.app.Application
import android.content.Context
import com.muratcangzm.dummysahibinden.repositories.FipeRepository
import com.muratcangzm.dummysahibinden.utils.DefaultDispatcher
import com.muratcangzm.dummysahibinden.utils.IODispatcher
import com.muratcangzm.dummysahibinden.utils.MainDispatcher
import com.muratcangzm.network.service.FipeAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @DefaultDispatcher
    fun provideDefaultDispatcher() = Dispatchers.Default

    @Provides
    @IODispatcher
    fun provideIODispatcher() = Dispatchers.IO

    @Provides
    @MainDispatcher
    fun provideMainDispatcher() = Dispatchers.Main


    @Provides
    @Singleton
    fun provideApplicationContext(@ApplicationContext application: Application): Context {
        return application.applicationContext

    }



    @Singleton
    @Provides
    fun provideFipeRepository(
        api: FipeAPI,
        @IODispatcher ioDispatcher: CoroutineDispatcher
    ): FipeRepository {
        return FipeRepository(api, ioDispatcher)
    }


}