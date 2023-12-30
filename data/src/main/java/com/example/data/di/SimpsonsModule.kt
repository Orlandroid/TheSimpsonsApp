package com.example.data.di


import com.example.data.bored.BoredApi
import com.example.data.bored.BoredRepositoryImpl
import com.example.data.simpsons.SimpsonsApi
import com.example.data.simpsons.SimpsonsRepositoryImpl
import com.example.domain.bored.BoredRepository
import com.example.domain.simpsons.SimpsonsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SimpsonsModule {


    private const val BASE_URL = "https://apisimpsons.fly.dev/api/"
    private const val SIMPSONS = "Simpsons"


    @Singleton
    @Provides
    @Named(SIMPSONS)
    fun provideRetrofitBored(okHttpClient: OkHttpClient): Retrofit = provideRetrofitGeneric(
        okHttpClient,
        BASE_URL
    )

    @Singleton
    @Provides
    fun provideSimpsonApi(@Named(SIMPSONS) retrofit: Retrofit): SimpsonsApi =
        retrofit.create(SimpsonsApi::class.java)

    @Provides
    @Singleton
    fun provideBoredRepository(
        simpsonsApi: SimpsonsApi,
    ): SimpsonsRepository = SimpsonsRepositoryImpl(simpsonsApi)


}