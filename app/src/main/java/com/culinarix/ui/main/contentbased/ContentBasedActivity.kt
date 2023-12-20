package com.culinarix.ui.main.contentbased

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import com.culinarix.R
import com.culinarix.databinding.ActivityContentBasedBinding
import com.culinarix.ui.ViewModelFactory
import com.culinarix.ui.authentication.login.LoginActivity
import com.culinarix.ui.main.MainActivity

class ContentBasedActivity : AppCompatActivity() {

    private lateinit var binding: ActivityContentBasedBinding
    private val viewModel: ContentBasedViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContentBasedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var edtContent = binding.edtContentb.text

        getSession()

        binding.btnNext.setOnClickListener {
//            Toast.makeText(this,"$edtContent",Toast.LENGTH_SHORT).show()
            val intent = Intent(this@ContentBasedActivity, MainActivity::class.java)
            intent.putExtra(MainActivity.EXTRA_PLACE_NAME, edtContent.toString())
            startActivity(intent)


        }

        binding.btnSkip.setOnClickListener {
            startActivity(Intent(this@ContentBasedActivity, MainActivity::class.java))
        }


    }

    private fun getSession() {
        viewModel.getSession().observe(this) { data ->
            if (data.isLogin == false) {
                val intent = Intent(this, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                finish()
            }
        }
    }
}