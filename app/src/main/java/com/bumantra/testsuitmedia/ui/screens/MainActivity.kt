package com.bumantra.testsuitmedia.ui.screens

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumantra.testsuitmedia.R
import com.bumantra.testsuitmedia.data.local.UserPreference
import com.bumantra.testsuitmedia.databinding.ActivityMainBinding
import com.bumantra.testsuitmedia.utils.isPalindrome

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnNext.setOnClickListener {
            val name = binding.edtName.text.toString()
            if (name.isNotEmpty()) {
                saveUser(name)
                val intent = Intent(this, SecondScreenActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Please enter Name text field", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnCheck.setOnClickListener {
            checkPalindrome(binding.edtPalindrome.text.toString())
        }

    }

    private fun checkPalindrome(text: String) {
        text.takeIf { it.isNotEmpty() }?.let {
            val message = if (isPalindrome(it)) "isPalindrome" else "Not palindrome"
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        } ?: run {
            Toast.makeText(this, "Please enter Palindrome text field to check", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun saveUser(name: String) {
        val userPreference = UserPreference(this)
        userPreference.setUser(name)
    }
}