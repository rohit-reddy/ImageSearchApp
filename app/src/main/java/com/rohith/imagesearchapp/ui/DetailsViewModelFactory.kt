package com.rohith.imagesearchapp.ui

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rohith.imagesearchapp.data.UnsplashPhotoModel

/**
 * Simple ViewModel factory that provides the UnsplashPhotoModel and context to the ViewModel.
 */
class DetailsViewModelFactory(
    private val unsplashPhotoModel: UnsplashPhotoModel,
    private val application: Application
) : ViewModelProvider.Factory{
    @Suppress("unchecked_cast")
    override fun<T : ViewModel?>create(modelClass:Class<T>):T{
        if(modelClass.isAssignableFrom(DetailsViewModel::class.java)){
            return DetailsViewModel(unsplashPhotoModel,application)as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}