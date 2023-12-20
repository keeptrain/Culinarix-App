package com.culinarix.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.culinarix.data.api.response.collab.RecommendedPlacesItem
import com.culinarix.databinding.TopRatedItemBinding

class CollabAdapter: ListAdapter<RecommendedPlacesItem, CollabAdapter.MyViewHolder>(DIFF_CALLBACK.DIFF_CALLBACK) {

    private lateinit var onItemClickAdapter : OnItemClickAdapter

    fun setItemClickAdapter(onItemClickAdapter : OnItemClickAdapter){
        this.onItemClickAdapter = onItemClickAdapter
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollabAdapter.MyViewHolder {
        val binding = TopRatedItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CollabAdapter.MyViewHolder, position: Int) {
        val data = getItem(position)
        holder.binding.btnTodetail.setOnClickListener {
            onItemClickAdapter.onItemClick(data)
        }
        holder.bind(data)
    }

    inner class MyViewHolder( val binding: TopRatedItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: RecommendedPlacesItem) {
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
        fun onItemClick(datta: RecommendedPlacesItem)
    }


    companion object DIFF_CALLBACK {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<RecommendedPlacesItem>() {
            override fun areItemsTheSame(oldItem: RecommendedPlacesItem, newItem: RecommendedPlacesItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: RecommendedPlacesItem, newItem: RecommendedPlacesItem): Boolean {
                return oldItem == newItem
            }
        }
    }

}