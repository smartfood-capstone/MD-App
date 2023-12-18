package com.syhdzn.capstoneapp.ui.result

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.syhdzn.capstoneapp.R
import com.syhdzn.capstoneapp.databinding.ActivityResultBinding
import com.syhdzn.capstoneapp.databinding.ActivityWelcomeBinding
import com.syhdzn.capstoneapp.ui.dashboard.DashboardActivity

class ResultActivity : AppCompatActivity() {


    private lateinit var binding: ActivityResultBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAction()
    }

    private fun setupAction() {
        binding.btnResult.setOnClickListener {
            startActivity(Intent(this, DashboardActivity::class.java))
        }
        binding.btnShare.setOnClickListener {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, title)
            val sharedText = "# Dibagikan dari Aplikasi Planet Ikan #"
            shareIntent.putExtra(Intent.EXTRA_TEXT, sharedText)
            startActivity(Intent.createChooser(shareIntent, "Bagikan via"))
        }
    }

    override fun onBackPressed() {
        val intent = Intent(this, DashboardActivity::class.java)
        startActivity(intent)
    }
}