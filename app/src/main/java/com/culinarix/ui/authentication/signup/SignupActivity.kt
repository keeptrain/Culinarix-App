package com.culinarix.ui.authentication.signup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import com.culinarix.R
import com.culinarix.databinding.ActivitySignupBinding

class SignupActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAction()

    }


    private fun setupAction() {
        actionBar()
    }

    private fun actionBar () {
        setSupportActionBar(binding.actionBar)
        with (supportActionBar) {
            this?.setDisplayHomeAsUpEnabled(true)
            title = ""
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

}