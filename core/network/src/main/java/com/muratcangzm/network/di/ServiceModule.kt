package com.muratcangzm.network.di

import com.muratcangzm.Constants
import com.muratcangzm.network.service.FipeAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {


    @Provides
    @Singleton
    fun provideBaseUrl() = Constants.BASE_URL


    @Singleton
    @Provides
    fun provideOkhttpClient() = OkHttpClient.Builder()
        .connectTimeout(30,TimeUnit.SECONDS)
        .readTimeout(30,TimeUnit.SECONDS)
        .writeTimeout(30,TimeUnit.SECONDS)
        .followRedirects(true)
        .followSslRedirects(true)
        .retryOnConnectionFailure(true)
        .build()

   @Provides
   @Singleton
   fun provideAPI(baseUrl:String, client: OkHttpClient): FipeAPI{
       return Retrofit
           .Builder()
           .baseUrl(baseUrl)
         //  .addConverterFactory() // FIXME: add dependency it will be fixed somehow idk
           .client(client)
           .build()
           .create(FipeAPI::class.java)

   }


}