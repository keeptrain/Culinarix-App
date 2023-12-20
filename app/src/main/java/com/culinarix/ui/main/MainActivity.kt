package com.culinarix.ui.main

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.culinarix.R
import com.culinarix.data.api.response.collab.RecommendedPlacesItem
import com.culinarix.data.api.response.content.MatchingRestaurantsItem
import com.culinarix.data.api.response.content.RecommendedContentPlacesItem
import com.culinarix.data.api.response.content.TopRatedPlacesItem
import com.culinarix.databinding.ActivityMainBinding
import com.culinarix.ui.ViewModelFactory
import com.culinarix.ui.main.adapter.CollabAdapter
import com.culinarix.ui.main.adapter.ContentAdapter
import com.culinarix.ui.main.adapter.PlaceAdapter
import com.culinarix.ui.main.adapter.SearchAdapter
import com.culinarix.ui.main.detail.DetailActivity
import com.culinarix.ui.main.profile.ProfileActivity
import com.culinarix.ui.utils.ResultState

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    private var user_id: String? = null

    private var placeName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.topRatedList.layoutManager = layoutManager

        dialogAds()
        setupAction()

        binding.cdBanner.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

        binding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    searchContent(newText)

                }
                return true
            }

        })


    }

    private fun setupAction() {

        placeName = intent.getStringExtra(EXTRA_PLACE_NAME)

        if (placeName != null) {
            getContent(placeName.toString())

        } else {
            viewModel.getUserId().observe(this) {
                if (it != null) {
                    user_id = it.userId

                    if (user_id?.toInt()!! < 200) {
                        getCollab(user_id.toString())

                    } else {
                        getTopRatedCollab()
                    }

                }
            }
        }

        viewModel.getDetailUser().observe(this) {
            if (it != null) {
                when (it) {
                    is ResultState.Loading -> {

                    }

                    is ResultState.Success -> {
                        binding.tvNameuser.text = getString(R.string.helloUser, it.data.name)
                    }

                    is ResultState.Error -> {
                        Toast.makeText(this, it.error, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }


    }

    private fun getTopRatedCollab() {
        viewModel.getTopRatedCollab().observe(this) { result ->
            if (result != null) {
                when (result) {
                    is ResultState.Loading -> {
                        binding.progressIndicator.visibility = View.VISIBLE

                    }

                    is ResultState.Success -> {
                        binding.progressIndicator.visibility = View.GONE
                        setDataTopColab(result.data.topRatedPlaces)

                    }

                    is ResultState.Error -> {
                        binding.progressIndicator.visibility = View.GONE
                    }
                }
            }
        }
    }

    private fun getCollab(user_id: String) {
        viewModel.getCollab(user_id).observe(this) { result ->
            if (result != null) {
                when (result) {
                    is ResultState.Loading -> {
                        binding.progressIndicator.visibility = View.VISIBLE


                    }

                    is ResultState.Success -> {
                        binding.progressIndicator.visibility = View.GONE
                        setDataCollab(result.data.recommendedPlaces)

                    }

                    is ResultState.Error -> {
                        binding.progressIndicator.visibility = View.GONE
                    }
                }

            }

        }
    }

    private fun getContent(place_name: String) {
        viewModel.getContent(place_name).observe(this) { result ->
            if (result != null) {
                when (result) {
                    is ResultState.Loading -> {
                        binding.progressIndicator.visibility = View.VISIBLE
                    }

                    is ResultState.Success -> {
                        binding.progressIndicator.visibility = View.GONE
                        setDataContent(result.data.recommendedPlaces)

                    }

                    is ResultState.Error -> {
                        binding.progressIndicator.visibility = View.GONE
                        viewModel.getUserId().observe(this) {
                            if (it != null) {
                                user_id = it.userId
                                Toast.makeText(this, "$user_id", Toast.LENGTH_SHORT).show()

                                if (user_id?.toInt()!! < 200) {
                                    getCollab(user_id.toString())

                                } else {
                                    getTopRatedCollab()
                                }

                            }
                        }
                    }
                }

            }

        }
    }

    fun searchContent(place_name: String) {
        if (place_name.isBlank()) {
            setupAction()

        } else {
            viewModel.searchContent(place_name).observe(this) { result ->
                if (result != null) {
                    when (result) {
                        is ResultState.Loading -> {
                            binding.progressIndicator.visibility = View.VISIBLE

                        }

                        is ResultState.Success -> {
                            binding.progressIndicator.visibility = View.GONE
                            setSearchContent(result.data.matchingRestaurants)
                        }

                        is ResultState.Error -> {
                            binding.progressIndicator.visibility = View.GONE
                        }
                    }
                }
            }

        }

    }


    private fun setDataTopColab(data: List<TopRatedPlacesItem>) {
        val adapter = PlaceAdapter()
        adapter.submitList(data)
        binding.topRatedList.adapter = adapter
        adapter.setItemClickAdapter(object : PlaceAdapter.OnItemClickAdapter {
            override fun onItemClick(datta: TopRatedPlacesItem) {
                val i = Intent(this@MainActivity, DetailActivity::class.java)
                i.putExtra(DetailActivity.EXTRA_PLACE_NAME, datta.placeName)
                i.putExtra(DetailActivity.EXTRA_DESC, datta.description)
                i.putExtra(DetailActivity.EXTRA_RATING, datta.culinaryRatings)
                i.putExtra(DetailActivity.EXTRA_IMGURL, datta.imageAddress)
                i.putExtra(DetailActivity.EXTRA_ADDRESS, datta.address)
                i.putExtra(DetailActivity.EXTRA_LOCADDRESS, datta.gmapsAddress)
                i.putExtra(DetailActivity.EXTRA_LAT, datta.lat)
                i.putExtra(DetailActivity.EXTRA_LONG, datta.long)
                startActivity(i)

            }

        })
    }

    private fun setDataCollab(data: List<RecommendedPlacesItem?>?) {
        val adapter = CollabAdapter()
        adapter.submitList(data)
        binding.topRatedList.adapter = adapter
        adapter.setItemClickAdapter(object : CollabAdapter.OnItemClickAdapter {
            override fun onItemClick(datta: RecommendedPlacesItem) {
                val i = Intent(this@MainActivity, DetailActivity::class.java)
                i.putExtra(DetailActivity.EXTRA_PLACE_NAME, datta.placeName)
                i.putExtra(DetailActivity.EXTRA_DESC, datta.description)
                i.putExtra(DetailActivity.EXTRA_RATING, datta.culinaryRatings)
                i.putExtra(DetailActivity.EXTRA_IMGURL, datta.imageAddress)
                i.putExtra(DetailActivity.EXTRA_ADDRESS, datta.address)
                i.putExtra(DetailActivity.EXTRA_LOCADDRESS, datta.gmapsAddress)
                i.putExtra(DetailActivity.EXTRA_LAT, datta.lat)
                i.putExtra(DetailActivity.EXTRA_LONG, datta.long)
                startActivity(i)
            }

        })
    }

    private fun setDataContent(data: List<RecommendedContentPlacesItem?>?) {
        val adapter = ContentAdapter()
        adapter.submitList(data)
        binding.topRatedList.adapter = adapter
        adapter.setItemClickAdapter(object : ContentAdapter.OnItemClickAdapter {
            override fun onItemClick(datta: RecommendedContentPlacesItem) {
                val i = Intent(this@MainActivity, DetailActivity::class.java)
                i.putExtra(DetailActivity.EXTRA_PLACE_NAME, datta.placeName)
                i.putExtra(DetailActivity.EXTRA_DESC, datta.description)
                i.putExtra(DetailActivity.EXTRA_RATING, datta.culinaryRatings)
                i.putExtra(DetailActivity.EXTRA_IMGURL, datta.imageAddress)
                i.putExtra(DetailActivity.EXTRA_ADDRESS, datta.address)
                i.putExtra(DetailActivity.EXTRA_LOCADDRESS, datta.gmapsAddress)
                i.putExtra(DetailActivity.EXTRA_LAT, datta.lat)
                i.putExtra(DetailActivity.EXTRA_LONG, datta.long)
                startActivity(i)
            }

        })
    }

    private fun setSearchContent(data: List<MatchingRestaurantsItem?>?) {
        val adapter = SearchAdapter()
        adapter.submitList(data)
        binding.topRatedList.adapter = adapter
        adapter.setItemClickAdapter(object : SearchAdapter.OnItemClickAdapter{
            override fun onItemClick(datta: MatchingRestaurantsItem) {
                val i = Intent(this@MainActivity, DetailActivity::class.java)
                i.putExtra(DetailActivity.EXTRA_PLACE_NAME, datta.placeName)
                i.putExtra(DetailActivity.EXTRA_DESC, datta.description)
                i.putExtra(DetailActivity.EXTRA_RATING, datta.culinaryRatings)
                i.putExtra(DetailActivity.EXTRA_IMGURL, datta.imageAddress)
                i.putExtra(DetailActivity.EXTRA_ADDRESS, datta.address)
                i.putExtra(DetailActivity.EXTRA_LOCADDRESS, datta.gmapsAddress)
                i.putExtra(DetailActivity.EXTRA_LAT, datta.lat)
                i.putExtra(DetailActivity.EXTRA_LONG, datta.long)
                startActivity(i)
            }

        })
    }

    private fun dialogAds(){
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(R.layout.ads_dialog)

        val close = dialog.findViewById<ImageView >(R.id.btn_close)

        close.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()

    }


    companion object {
        var EXTRA_PLACE_NAME = "extra_place_name"
    }

}