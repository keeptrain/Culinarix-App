package com.culinarix.ui.authentication.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.culinarix.R
import com.culinarix.data.model.UserModel
import com.culinarix.databinding.ActivityLoginBinding
import com.culinarix.ui.ViewModelFactory
import com.culinarix.ui.authentication.signup.SignupActivity
import com.culinarix.ui.main.MainActivity
import com.culinarix.ui.utils.ResultState

class LoginActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLoginBinding

    private val viewModel : LoginViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAction()
    }

    private fun setupAction() {
        getSession()
        login()
        validationLayout()
        textSignup()
    }

    private fun login() {

        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            if (!binding.emailEditText.isValid()) {
                return@setOnClickListener
            }

            if (!binding.passwordEditText.isValid()) {
                return@setOnClickListener
            }

            if (email.isNotEmpty() && password.isNotEmpty()) {
                viewModel.login(email, password)
            } else {
                val message = getString(R.string.loginError)
                showToast(message)
            }

        }

        viewModel.loginResult.observe(this ) { result ->
            if (result != null) {
                when (result) {
                    is ResultState.Loading -> {
                        showLoading(true)
                        buttonLayout(false)
                    }
                    is ResultState.Success -> {
                        val message = result.data.message
                        showToast(message)
                        showLoading(false)
                        saveSession(UserModel(result.data.data!!.token))
                        getSession()

                    }
                    is ResultState.Error -> {
                        val message = result.error
                        showLoading(false)
                        buttonLayout(true)
                        showAlertDialog(message)
                    }
                }
            }
        }
    }

    private fun getSession() {
        viewModel.getSession().observe(this) {user ->
            if (user.isLogin) {
                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                finish()
            }
        }
    }

    private fun saveSession(user : UserModel) {
        viewModel.saveSession(user)
    }

    private fun validationLayout() {
        val emailLayout = binding.emailEdtLayout
        binding.emailEditText.setEmailLayout(emailLayout)

        val passwordLayout = binding.passwordEdtLayout
        binding.passwordEditText.setPasswordLayout(passwordLayout)
    }

    private fun buttonLayout(isLoading: Boolean) {
        binding.loginButton.isEnabled = isLoading
    }

    private fun textSignup() {

        val text = SpannableString(binding.dontHaveAcc.text.toString())

        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                val signUpIntent = Intent(this@LoginActivity, SignupActivity::class.java)
                startActivity(signUpIntent)
            }
        }

        val startIndex = text.indexOf(" Signup")
        val endIndex = startIndex + " Signup".length

        text.setSpan(clickableSpan, startIndex , endIndex , Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.dontHaveAcc.text = text
        binding.dontHaveAcc.movementMethod = LinkMovementMethod.getInstance()

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

    private fun showToast(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressIndicator.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

}