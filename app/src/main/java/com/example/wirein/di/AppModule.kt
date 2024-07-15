package com.example.wirein.di

import com.example.wirein.api.WIREinAPI
import com.example.wirein.repos.HomeRepo
import com.example.wirein.util.AppConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(AppConstants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideInstagramApi(retrofit: Retrofit): WIREinAPI = retrofit.create(WIREinAPI::class.java)

    @Provides
    @Singleton
    fun providerInstagramRepo(api: WIREinAPI) = HomeRepo(api)
}

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class ApplicationScope