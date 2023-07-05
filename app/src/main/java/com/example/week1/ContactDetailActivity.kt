package com.example.week1

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.MenuItem
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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
        val email = intent.getStringExtra("email")

        binding.tvName.text = name
        binding.tvPhoneNumber.text = number

        if(imageUri != null) {
            binding.ivProfile.setImageDrawable(getDrawableFromUri(this, imageUri))
        } else {
            binding.ivProfile.setImageResource(R.drawable.image_contact_default)
        }

        checkPermissions()

        binding.fabCall.setOnClickListener{
            val callIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$number"))
            startActivity(callIntent)
        }

        binding.fabMessage.setOnClickListener{
            val messageIntent = Intent(Intent.ACTION_VIEW, Uri.parse("sms:$number"))
            startActivity(messageIntent)
        }

        binding.fabEmail.setOnClickListener{
            if(email == null || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "이메일 정보를 확인하세요", Toast.LENGTH_SHORT).show()
            } else {
                val emailIntent = Intent(Intent.ACTION_SEND)
                emailIntent.putExtra(Intent.EXTRA_EMAIL, email)
                startActivity(emailIntent)
            }
        }
    }

    private fun checkPermissions() {
        val statusCall = ContextCompat.checkSelfPermission(this, "android.permission.CALL_PHONE")
        if(statusCall == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, arrayOf<String>("android.permission.CALL_PHONE"), 100)
        }
        val statusSms = ContextCompat.checkSelfPermission(this, "android.permission.SEND_SMS")
        if(statusSms == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, arrayOf<String>("android.permission.SEND_SMS"), 100)
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