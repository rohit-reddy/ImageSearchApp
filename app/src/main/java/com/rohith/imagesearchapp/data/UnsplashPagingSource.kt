package com.rohith.imagesearchapp.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.rohith.imagesearchapp.network.PhotosApiService
import retrofit2.HttpException
import java.io.IOException

private const val UNSPLASH_STARTING_PAGE_INDEX = 1

class UnsplashPagingSource(
    private val photosApiService: PhotosApiService,
    private val query: String
) : PagingSource<Int, UnsplashPhotoModel>() {


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UnsplashPhotoModel> {
        val position = params.key ?: UNSPLASH_STARTING_PAGE_INDEX

        return try {
            val response = photosApiService.getSearchPhotos(query, position, params.loadSize)
            val photos = response.results
            LoadResult.Page(
                data = photos,
                prevKey = if (position == UNSPLASH_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (photos.isEmpty()) null else position + 1
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }

    }

    override fun getRefreshKey(state: PagingState<Int, UnsplashPhotoModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}