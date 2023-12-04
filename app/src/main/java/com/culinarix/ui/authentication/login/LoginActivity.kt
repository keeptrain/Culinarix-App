package com.culinarix.ui.authentication.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import com.culinarix.R
import com.culinarix.databinding.ActivityLoginBinding
import com.culinarix.ui.authentication.signup.SignupActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setupAction()
    }

    private fun setupAction() {
        validationLayout()
        textSignup()
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
}