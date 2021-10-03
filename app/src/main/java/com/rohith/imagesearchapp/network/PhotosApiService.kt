package com.rohith.imagesearchapp.network

import com.rohith.imagesearchapp.BuildConfig
import com.rohith.imagesearchapp.data.UnsplashPhotoModel
import com.rohith.imagesearchapp.data.UnsplashResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

const val BASE_URL = "https://api.unsplash.com/"
const val CLIENT_ID = BuildConfig.UNSPLASH_ACCESS_KEY


/**
 * A public interface that exposes the [getPhotos] [getSearchPhotos] method
 */
interface PhotosApiService {
    /**
     * Returns a Coroutine [List] of [getPhotos] which can be fetched in a Coroutine scope.
     * The @GET annotation indicates that the "photos" endpoint will be requested with the GET
     * HTTP method
     */
    @Headers("Authorization: Client-ID $CLIENT_ID")
    @GET("photos")
    suspend fun getPhotos(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): List<UnsplashPhotoModel>


    /**
     * Returns a Coroutine [List] of [getSearchPhotos] which can be fetched in a Coroutine scope.
     * The @GET annotation indicates that the "search/photos" endpoint will be requested with the GET
     * HTTP method
     */
    @Headers("Authorization: Client-ID $CLIENT_ID")
    @GET("search/photos")
    suspend fun getSearchPhotos(
        @Query("query") query: String,
        @Query("page") page: Int, @Query("per_page") perPage: Int
    ): UnsplashResponse
}



