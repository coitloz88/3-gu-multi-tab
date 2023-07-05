package com.example.week1

import android.content.ContentProviderOperation
import android.content.Intent
import android.content.OperationApplicationException
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.RemoteException
import android.provider.ContactsContract
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.week1.databinding.ActivityContactDetailBinding
import com.example.week1.utils.getDrawableFromUri

class ContactDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityContactDetailBinding
    private var id: Long = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        id = intent.getLongExtra("id", 0)
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

        checkPermissions()

        binding.fabCall.setOnClickListener{
            val callIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$number"))
            startActivity(callIntent)
        }

        binding.fabMessage.setOnClickListener{
            val messageIntent = Intent(Intent.ACTION_VIEW, Uri.parse("sms:$number"))
            startActivity(messageIntent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.contact_detail_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            R.id.action_delete -> {
                return if(id.compareTo(0) == 0) {
                    Toast.makeText(this, "연락처 삭제 실패", Toast.LENGTH_SHORT).show()
                    Log.e(TAG, "contact id is 0!")
                    false
                } else {
                    deleteContact(id)
                    finish()
                    true
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
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

    private fun deleteContact(contactId: Long) {
        val ops = ArrayList<ContentProviderOperation>()
        val args = arrayOf<String>(contactId.toString())
        ops.add(ContentProviderOperation.newDelete(ContactsContract.RawContacts.CONTENT_URI)
            .withSelection(ContactsContract.RawContacts.CONTACT_ID + "=?", args)
            .build())
        try {
            contentResolver.applyBatch(ContactsContract.AUTHORITY, ops)
            Toast.makeText(this, "연락처를 삭제했습니다", Toast.LENGTH_SHORT).show()
        } catch (e: RemoteException) {
            e.printStackTrace()
        } catch (e: OperationApplicationException) {
            e.printStackTrace()
        }
    }

    companion object {
        private const val TAG = "ContactDetailActivity"
    }
}