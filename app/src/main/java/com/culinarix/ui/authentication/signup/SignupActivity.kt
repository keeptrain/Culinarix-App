package com.culinarix.ui.authentication.signup

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.culinarix.R
import com.culinarix.databinding.ActivitySignupBinding
import com.culinarix.ui.ViewModelFactory
import com.culinarix.ui.authentication.login.LoginViewModel
import com.culinarix.ui.utils.ResultState

class SignupActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySignupBinding

    private val viewModel : SignupViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAction()

    }

    private fun setupAction() {
        signup()
        validateLayout()
    }

    private fun signup() {
        binding.signupBtn.setOnClickListener {
            val name = binding.nameEdt.text.toString()
            val email = binding.emailEdt.text.toString()
            val password = binding.passwordEdt.text.toString()
            val age = binding.ageEdt.text.toString()
            val domicile = binding.domicileEdt.text.toString()

            if (!binding.nameEdt.isValid()) {
                return@setOnClickListener
            }

            if (!binding.emailEdt.isValid()) {
                return@setOnClickListener
            }

            if (!binding.passwordEdt.isValid()) {
                return@setOnClickListener
            }

            if (!binding.ageEdt.isValid()) {
                return@setOnClickListener
            }

            if (!binding.domicileEdt.isValid()) {
                return@setOnClickListener
            }


            if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && age.isNotEmpty() && domicile.isNotEmpty()) {
                viewModel.register(name, email, password, age.toInt(), domicile)
            } else {
                val message = getString(R.string.signupError)
                showAlertDialog(message)
            }


        }

        viewModel.signUpResult.observe(this) { result ->
            if (result != null) {
                when (result) {
                    ResultState.Loading -> {
                        showLoading(true)
                    }
                    is ResultState.Success -> {
                        showLoading(false)
                        val response = result.data.message
                        showAlertDialog(response)

                    }
                    is ResultState.Error -> {
                        showLoading(false)
                        val response = result.error
                        showAlertDialog(response)
                    }
                }
            }
        }

    }



    private fun validateLayout() {
        val nameLayout = binding.nameEdtLayout
        binding.nameEdt.setNameLayout(nameLayout)

        val emailLayout = binding.emailEdtLayout
        binding.emailEdt.setEmailLayout(emailLayout)

        val passwordLayout = binding.passwordEdtLayout
        binding.passwordEdt.setPasswordLayout(passwordLayout)

        val ageLayout = binding.ageEdtLayout
        binding.ageEdt.setAgeLayout(ageLayout)

        val domiLayout = binding.domicileEdtLayout
        binding.domicileEdt.setDomicileLayout(domiLayout)

    }

    private fun showAlertDialog(errorMessage: String?) {
        AlertDialog.Builder(this).apply {
            setMessage(errorMessage)
            setPositiveButton("OK") { dialog , _ ->
                dialog.dismiss()
            }
            create()
            show()
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressIndicator.visibility = if (isLoading) View.VISIBLE else View.GONE
    }



}