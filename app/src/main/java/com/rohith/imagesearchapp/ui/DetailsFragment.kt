package com.rohith.imagesearchapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.rohith.imagesearchapp.databinding.FragmentDetailsBinding

class DetailsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val application = requireNotNull(activity).application
        val binding = FragmentDetailsBinding.inflate(inflater)
        binding.lifecycleOwner = this

        val unsplashPhotoModel = DetailsFragmentArgs.fromBundle(requireArguments()).selectedPhoto
        val viewModelFactory = DetailsViewModelFactory(unsplashPhotoModel, application)
        binding.viewModel = ViewModelProvider(
            this, viewModelFactory).get(DetailsViewModel::class.java)

        return binding.root
    }
}