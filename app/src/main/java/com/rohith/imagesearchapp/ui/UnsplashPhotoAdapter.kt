package com.rohith.imagesearchapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.rohith.imagesearchapp.data.UnsplashPhotoModel
import com.rohith.imagesearchapp.databinding.GridViewItemBinding

class UnsplashPhotoAdapter(private val onClickListener: OnClickListener) :
    PagingDataAdapter<UnsplashPhotoModel, UnsplashPhotoAdapter.PhotoViewHolder>(
        DiffCallback) {

    /**
     * Create new [RecyclerView] item views (invoked by the layout manager)
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        return PhotoViewHolder(GridViewItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    /**
     * Replaces the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val photo = getItem(position)
        holder.itemView.setOnClickListener {
            photo?.let { it1 -> onClickListener.onClick(it1) }
        }
        if (photo != null) holder.bind(photo)
    }



    class PhotoViewHolder(private val binding: GridViewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(unsplashPhotoModel: UnsplashPhotoModel){
            binding.imageUrl = unsplashPhotoModel.urls
            binding.username = unsplashPhotoModel.user
            binding.executePendingBindings()
        }
    }

    /**
     * Allows the RecyclerView to determine which items have changed when the [List] of [UnsplashPhotoModel]
     * has been updated.
     */
    companion object DiffCallback : DiffUtil.ItemCallback<UnsplashPhotoModel>() {
        override fun areItemsTheSame(oldItem: UnsplashPhotoModel, newItem: UnsplashPhotoModel) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: UnsplashPhotoModel, newItem: UnsplashPhotoModel) =
            oldItem == newItem
    }

    /**
     * Custom listener that handles clicks on [RecyclerView] items.  Passes the [UnsplashPhotoModel]
     * associated with the current item to the [onClick] function.
     * @param clickListener lambda that will be called with the current [UnsplashPhotoModel]
     */
    class OnClickListener(val clickListener: (unsplashPhotoModel: UnsplashPhotoModel) -> Unit) {
        fun onClick(unsplashPhotoModel:UnsplashPhotoModel) = clickListener(unsplashPhotoModel)
    }
}