package com.bumantra.testsuitmedia.ui.screens

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumantra.testsuitmedia.R
import com.bumantra.testsuitmedia.data.local.UserPreference
import com.bumantra.testsuitmedia.databinding.ActivitySecondScreenBinding

class SecondScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySecondScreenBinding
    private lateinit var mUserPreference: UserPreference
    private lateinit var user: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySecondScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mUserPreference = UserPreference(this)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        showUser()
        onClick()
        handleIntent(intent)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        handleIntent(intent)
    }

    private fun onClick() {
        binding.topAppBar.setNavigationOnClickListener {
            finish()
        }
        binding.btnChoose.setOnClickListener {
            val intent = Intent(this, ThirdScreenActivity::class.java)
            startActivity(intent)
        }
    }

    private fun handleIntent(intent: Intent?) {
        val fullName = intent?.getStringExtra("fullName") ?: getString(R.string.selected_user_name)
        binding.tvSelectedName.text = fullName
    }


    private fun showUser() {
        user = mUserPreference.getUser()
        binding.tvSecondName.text = user
    }

}