package com.culinarix.ui.authentication.signup

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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
        validateLayout()
    }

    private fun actionBar () {
        setSupportActionBar(binding.actionBar)
        with (supportActionBar) {
            this?.setDisplayHomeAsUpEnabled(true)
            title = ""
        }

    }

    private fun validateLayout() {
        val emailLayout = binding.emailEdtLayout
        binding.emailEdt.setEmailLayout(emailLayout)

        val passwordLayout = binding.passwordEdtLayout
        binding.passwordEdt.setPasswordLayout(passwordLayout)

        val domiLayout = binding.domicileEdtLayout
        binding.domicileEdt.setDomicilieLayout(domiLayout)

    }






}