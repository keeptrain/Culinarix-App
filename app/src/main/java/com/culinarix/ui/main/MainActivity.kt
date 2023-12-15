package com.culinarix.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.culinarix.data.api.response.content.TopRatedPlacesItem
import com.culinarix.databinding.ActivityMainBinding
import com.culinarix.ui.ViewModelFactory
import com.culinarix.ui.authentication.login.LoginActivity
import com.culinarix.ui.main.adapter.PlaceAdapter
import com.culinarix.ui.main.profile.ProfileActivity
import com.culinarix.ui.utils.ResultState

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    private val viewModel : MainViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.topRatedList.layoutManager = layoutManager

        val itemDecoration = DividerItemDecoration(this,layoutManager.orientation)
        binding.topRatedList.addItemDecoration(itemDecoration)


        binding.testProfile.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

        setupAction()

    }

    private fun setupAction() {
        getTopRated()
        logout()
    }

    private fun getTopRated() {
        viewModel.getTopRated().observe(this) { result ->
            if (result != null) {
                when (result) {
                    is ResultState.Loading -> {


                    }

                    is ResultState.Success -> {

                        setDataTopRated(result.data.topRatedPlaces)

                    }

                    is ResultState.Error -> {
                    }
                }
            }
        }
    }

    private fun setDataTopRated(data : List<TopRatedPlacesItem>) {
        val adapter = PlaceAdapter()
        adapter.submitList(data)
        binding.topRatedList.adapter = adapter
    }

    private fun logout() {
        binding.testLogout.setOnClickListener {
            viewModel.deleteSession()
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }
    }

}