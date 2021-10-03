package com.rohith.imagesearchapp.ui

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import com.rohith.imagesearchapp.R
import com.rohith.imagesearchapp.databinding.FragmentGalleryBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GalleryFragment : Fragment() {


    private val viewmodel by viewModels<GalleryViewModel>()
    private lateinit var binding : FragmentGalleryBinding

    /**
     * Inflates the layout with Data Binding, sets its lifecycle owner to the OverviewFragment
     * to enable Data Binding to observe LiveData, and sets up the RecyclerView with an adapter.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGalleryBinding.inflate(inflater)

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

        val adapter = UnsplashPhotoAdapter()

        binding.apply {
            photosGrid.setHasFixedSize(true)
            photosGrid.adapter = adapter
        }

        viewmodel.photos.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }

        adapter.addLoadStateListener {
            binding.apply {
                progressBar.isVisible = it.source.refresh is LoadState.Loading
                photosGrid.isVisible = it.source.refresh is LoadState.NotLoading
                statusImage.isVisible = it.source.refresh is LoadState.Error

                //empty view
                if(it.source.refresh is LoadState.NotLoading && it.append.endOfPaginationReached &&
                        adapter.itemCount < 1){
                    photosGrid.isVisible = false
                    statusImage.isVisible = true
                }else{
                    statusImage.isVisible = false
                }
            }
        }

        setHasOptionsMenu(true)
        return binding.root
    }


    /**
     * Inflates the overflow menu that contains filtering options.
     */
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.overflow_menu, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    binding.photosGrid.scrollToPosition(0)
                    viewmodel.searchQuery(it)
                    searchView.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }
}