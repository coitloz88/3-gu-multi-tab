package com.example.week1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class ContactDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_detail)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}