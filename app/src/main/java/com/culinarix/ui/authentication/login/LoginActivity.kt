package com.culinarix.ui.authentication.login

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.culinarix.R
import com.culinarix.data.model.UserModel
import com.culinarix.databinding.ActivityLoginBinding
import com.culinarix.ui.ViewModelFactory
import com.culinarix.ui.authentication.signup.SignupActivity
import com.culinarix.ui.main.contentbased.ContentBasedActivity
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
                        val message = result.data.message.toString()
                        saveSession(UserModel(result.data.data!!.userId,result.data.data.token))
                        dialogSukses(message)
                        showLoading(false)
                    }
                    is ResultState.Error -> {
                        dialogGagal()
                        showLoading(false)
                        buttonLayout(true)

                    }

                }
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


    private fun dialogSukses(msg:String){
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(R.layout.success_dialog)

        val next = dialog.findViewById<Button>(R.id.btn_success)
        val message = dialog.findViewById<TextView>(R.id.success_message)

        message.text = msg

        next.setOnClickListener {
            val intent = Intent(this, ContentBasedActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }

        dialog.show()
    }

    private fun dialogGagal(){
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(R.layout.failed_dialog)

        val close = dialog.findViewById<Button>(R.id.btn_failed)


        close.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()

    }

    private fun showToast(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressIndicator.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

}