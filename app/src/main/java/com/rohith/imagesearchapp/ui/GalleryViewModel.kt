package com.rohith.imagesearchapp.ui

import androidx.lifecycle.*
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.cachedIn
import com.rohith.imagesearchapp.data.UnsplashRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


enum class PhotoApiStatus { LOADING, ERROR, DONE }

@HiltViewModel
class GalleryViewModel @Inject constructor(private val unsplashRepository: UnsplashRepository) : ViewModel() {

    private val _query = MutableLiveData<String>("India")

    private val _status = MutableLiveData<PhotoApiStatus>()

    val status: LiveData<PhotoApiStatus>
        get() = _status

    val photos = _query.switchMap {queryString ->
        unsplashRepository.getSearchResults(queryString).cachedIn(viewModelScope)
    }

    fun searchQuery(query: String){
        _query.value = query
    }
}