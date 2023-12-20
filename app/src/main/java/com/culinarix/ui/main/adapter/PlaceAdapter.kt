package com.culinarix.ui.main.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.culinarix.data.api.response.content.RecommendedContentPlacesItem
import com.culinarix.data.api.response.content.TopRatedPlacesItem
import com.culinarix.databinding.TopRatedItemBinding
import com.culinarix.ui.main.detail.DetailActivity

class PlaceAdapter: ListAdapter<TopRatedPlacesItem, PlaceAdapter.MyViewHolder>(DIFF_CALLBACK.DIFF_CALLBACK) {

    private lateinit var onItemClickAdapter : OnItemClickAdapter

    fun setItemClickAdapter(onItemClickAdapter : OnItemClickAdapter){
        this.onItemClickAdapter = onItemClickAdapter
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceAdapter.MyViewHolder {
        val binding = TopRatedItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PlaceAdapter.MyViewHolder, position: Int) {
        val data = getItem(position)
        holder.binding.btnTodetail.setOnClickListener {
            onItemClickAdapter.onItemClick(data)

        }
        holder.bind(data)
    }

    inner class MyViewHolder(val binding: TopRatedItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: TopRatedPlacesItem) {
            binding.apply {
                Glide.with(itemView.context)
                    .load(data.imageAddress)
                    .into(ivStory)
                tvName.text = data.placeName
                tvDescription.text = data.description
                tvCategory.text = data.category
                tvRating.text = data.culinaryRatings.toString()
                ratingbarMain.rating = data.culinaryRatings?.toFloat()!!
            }
        }
    }

    interface OnItemClickAdapter{
        fun onItemClick(datta: TopRatedPlacesItem)
    }

    companion object DIFF_CALLBACK {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TopRatedPlacesItem>() {
            override fun areItemsTheSame(oldItem: TopRatedPlacesItem, newItem: TopRatedPlacesItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: TopRatedPlacesItem, newItem: TopRatedPlacesItem): Boolean {
                return oldItem == newItem
            }
        }
    }

}