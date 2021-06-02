package com.coooldoggy.noearthnous.model.network

import com.coooldoggy.noearthnous.BASE_API_URL
import com.coooldoggy.noearthnous.BuildConfig
import com.coooldoggy.noearthnous.model.helper.UserApiHelper
import com.coooldoggy.noearthnous.model.helper.UserApiImpl
import com.coooldoggy.noearthnous.model.service.UserApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_API_URL)
        .client(okHttpClient)
        .build()


    @Provides
    @Singleton
    fun provideUserApiService(retrofit: Retrofit) = retrofit.create(UserApiService::class.java)

    @Provides
    @Singleton
    fun provideUserApiHelper(userApiImpl: UserApiImpl): UserApiHelper = userApiImpl

    @Singleton
    @Provides
    fun provideOkHttpClient() = if (BuildConfig.DEBUG){
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }else{
        OkHttpClient
            .Builder()
            .build()
    }
}