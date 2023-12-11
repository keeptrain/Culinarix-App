package com.culinarix.ui.authentication.signup

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
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
        validateLayout()
        testRegister()
    }

    private fun testRegister() {
        binding.signupBtn.setOnClickListener {
            val name = binding.nameEdt.text.toString()
            val email = binding.emailEdt.text.toString()
            val password = binding.passwordEdt.text.toString()
            val age = binding.ageEdt.text.toString()
            val domicilie = binding.domicileEdt.text.toString()


            viewModel.register(name, email, password,age.toInt(), domicilie)

        }

        viewModel.signUpResult.observe(this) { result ->
            if (result != null) {
                when (result) {
                    ResultState.Loading -> {
                        //showLoading(true)
                    }
                    is ResultState.Success -> {
                        //showLoading(false)
                        val response = result.data.message
                        showAlertDialog(response)

                    }
                    is ResultState.Error -> {
                        //showLoading(false)
                        //val response = result.error
                        //showAlertDialog(response)
                    }
                }
            }
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






}