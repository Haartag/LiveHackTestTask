package com.valerytimofeev.livehacktesttask.di

import com.google.gson.GsonBuilder
import com.valerytimofeev.livehacktesttask.data.remote.LifeHackApi
import com.valerytimofeev.livehacktesttask.repository.LifeHackRepository
import com.valerytimofeev.livehacktesttask.utility.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideLifeHackRepository(
        api: LifeHackApi
    ) = LifeHackRepository(api = api)

    @Singleton
    @Provides
    fun provideLifeHackApi(): LifeHackApi {
        val gson = GsonBuilder()
            .setLenient()
            .create()
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(BASE_URL)
            .build()
            .create(LifeHackApi::class.java)
    }

}