package com.rohith.imagesearchapp.di

import com.rohith.imagesearchapp.network.BASE_URL
import com.rohith.imagesearchapp.network.PhotosApiService
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
    /**
     * Use the Retrofit builder to build a retrofit object using a Moshi converter with our Moshi
     * object.
     */
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()

//    /**
//     * Build the Moshi object that Retrofit will be using, making sure to add the Kotlin adapter for
//     * full Kotlin compatibility.
//     */
//    @Provides
//    @Singleton
//    fun provideMoshi() : Moshi =
//        Moshi.Builder()
//            .add(KotlinJsonAdapterFactory())
//            .build()

    /**
     * A public Api object that exposes the Retrofit service
     */
    @Provides
    @Singleton
    fun providePhotosApi(retrofit: Retrofit) : PhotosApiService =
        retrofit.create(PhotosApiService::class.java)
}