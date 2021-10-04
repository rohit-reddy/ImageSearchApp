package com.rohith.imagesearchapp.ui

import android.app.Application
import androidx.lifecycle.*
import com.rohith.imagesearchapp.R
import com.rohith.imagesearchapp.data.UnsplashPhotoModel

/**
 *  The [ViewModel] associated with the [DetailsFragment], containing information about the selected
 *  [UnsplashPhotoModel].
 */
class DetailsViewModel( unsplashPhotoModel: UnsplashPhotoModel,
                       app: Application
) : AndroidViewModel(app) {

    // The internal MutableLiveData for the selected photo
    private val _selectedPhoto = MutableLiveData<UnsplashPhotoModel>()

    // The external LiveData for the selectedPhoto
    val selectedPhoto: LiveData<UnsplashPhotoModel>
        get() = _selectedPhoto

    // Initialize the _selectedPhoto MutableLiveData
    init {
        _selectedPhoto.value = unsplashPhotoModel
    }

    // The displayCreatorName formatted Transformation Map LiveData
    val displayCreatorName = Transformations.map(selectedPhoto) {
        app.applicationContext.getString(R.string.display_username, it.user.username)
    }

    // The displayDetailDesc formatted Transformation Map LiveData
    val displayDetailDesc= Transformations.map(selectedPhoto) {
        app.applicationContext.getString(R.string.display_description,
            it.description ?: "Sorry no description available!"
            )
    }
}
