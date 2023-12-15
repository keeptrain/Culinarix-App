package com.culinarix.ui.main.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.culinarix.R
import com.culinarix.databinding.ActivityMainBinding
import com.culinarix.databinding.ActivityProfileBinding
import com.culinarix.ui.ViewModelFactory
import com.culinarix.ui.authentication.login.LoginViewModel
import com.culinarix.ui.utils.ResultState

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding

    private val viewModel : ProfileViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getDetailUser().observe(this) {result ->
            if (result != null) {
                when (result) {
                    is ResultState.Loading -> {

                    }

                    is ResultState.Success -> {
                        val data = result.data
                        binding.name.text = data.email
                        binding.email.text = data.name

                    }

                    is ResultState.Error -> {
                        val message = result.error
                        //showToast(message)
                    }

                    else -> {}
                }
            }
        }

    }
}