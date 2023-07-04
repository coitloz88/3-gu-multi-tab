package com.example.week1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.example.week1.databinding.ActivityContactDetailBinding
import com.example.week1.utils.getDrawableFromUri

class ContactDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityContactDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val name = intent.getStringExtra("name")
        val number = intent.getStringExtra("number")
        val imageUri = intent.getStringExtra("imageUri")

        binding.tvName.text = name
        binding.tvPhoneNumber.text = number

        if(imageUri != null) {
            binding.ivProfile.setImageDrawable(getDrawableFromUri(this, imageUri))
        } else {
            binding.ivProfile.setImageResource(R.drawable.image_contact_default)
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}