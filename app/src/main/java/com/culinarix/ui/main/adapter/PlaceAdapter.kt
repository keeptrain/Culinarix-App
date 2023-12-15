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
import com.culinarix.data.api.response.content.TopRatedPlacesItem
import com.culinarix.databinding.TopRatedItemBinding
import com.culinarix.ui.main.detail.DetailActivity

class PlaceAdapter: ListAdapter<TopRatedPlacesItem, PlaceAdapter.MyViewHolder>(DIFF_CALLBACK.DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceAdapter.MyViewHolder {
        val binding = TopRatedItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PlaceAdapter.MyViewHolder, position: Int) {
        val data = getItem(position)
        holder.bind(data)
    }

    inner class MyViewHolder(private val binding: TopRatedItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: TopRatedPlacesItem) {
            binding.apply {
                Glide.with(itemView.context)
                    .load(data.imageAddress)
                    .into(ivStory)
                tvName.text = data.placeName
                tvDescription.text = data.description
            }

            /*itemView.setOnClickListener{
                val intent = Intent(itemView.context, DetailActivity::class.java)
                intent.putExtra("Story", story)

                val optionsCompat: ActivityOptionsCompat =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(
                        itemView.context as Activity,
                        Pair(binding.ivStory,"photo"),
                        Pair(binding.tvName,"name"),
                        Pair(binding.tvDescription,"description")
                    )
                itemView.context.startActivity(intent, optionsCompat.toBundle())
            }*/
        }
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