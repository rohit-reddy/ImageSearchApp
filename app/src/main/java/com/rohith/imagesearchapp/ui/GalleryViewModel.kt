package com.rohith.imagesearchapp.ui

import androidx.lifecycle.*
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.cachedIn
import com.rohith.imagesearchapp.data.UnsplashPhotoModel
import com.rohith.imagesearchapp.data.UnsplashRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


enum class PhotoApiStatus { LOADING, ERROR, DONE }

@HiltViewModel
class GalleryViewModel @Inject constructor(private val unsplashRepository: UnsplashRepository) : ViewModel() {

    private val _query = MutableLiveData<String>("India")

    private val _status = MutableLiveData<PhotoApiStatus>()

    val status: LiveData<PhotoApiStatus>
        get() = _status


    // LiveData to handle navigation to the selected photo
    private val _navigateToSelectedPhoto = MutableLiveData<UnsplashPhotoModel>()
    val navigateToSelectedPhoto: LiveData<UnsplashPhotoModel>
        get() = _navigateToSelectedPhoto

    val photos = _query.switchMap {queryString ->
        unsplashRepository.getSearchResults(queryString).cachedIn(viewModelScope)
    }


    /**
     * When the photo is clicked, set the [_navigateToSelectedPhoto] [MutableLiveData]
     * @param unsplashRepository The [UnsplashPhotoModel] that was clicked on.
     */
    fun displayPhotoDetails(unsplashRepository: UnsplashPhotoModel) {
        _navigateToSelectedPhoto.value = unsplashRepository
    }


    /**
     * After the navigation has taken place, make sure _navigateToSelectedPhoto is set to null
     */
    fun displayPhotoDetailsComplete() {
        _navigateToSelectedPhoto.value = null
    }

    fun searchQuery(query: String){
        _query.value = query
    }
}