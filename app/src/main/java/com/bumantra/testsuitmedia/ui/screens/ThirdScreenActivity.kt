package com.bumantra.testsuitmedia.ui.screens

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumantra.testsuitmedia.R
import com.bumantra.testsuitmedia.databinding.ActivityThirdScreenBinding
import com.bumantra.testsuitmedia.ui.adapter.LoadingStateAdapter
import com.bumantra.testsuitmedia.ui.adapter.UserAdapter
import com.bumantra.testsuitmedia.ui.viemodel.UserViewModel
import com.bumantra.testsuitmedia.utils.ViewModelFactory

class ThirdScreenActivity : AppCompatActivity() {
    private val viewModel by viewModels<UserViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityThirdScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityThirdScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userAdapter = UserAdapter()
        userAdapter.addLoadStateListener { loadState ->
            binding.progressBar.isVisible = loadState.source.refresh is LoadState.Loading

            if (loadState.source.refresh is LoadState.Error) {
                val errorState = loadState.source.refresh as LoadState.Error
                Toast.makeText(this, "Error: ${errorState.error.message}", Toast.LENGTH_SHORT).show()
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        viewModel.getAllUser.observe(this) {
            userAdapter.submitData(lifecycle, it)
        }

        binding.thirdAppBar.setNavigationOnClickListener {
            finish()
        }
        binding.rvUser.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = userAdapter.withLoadStateFooter(
                footer = LoadingStateAdapter {
                    userAdapter.retry()
                }
            )
        }
    }
}