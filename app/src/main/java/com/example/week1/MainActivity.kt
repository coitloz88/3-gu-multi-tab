package com.example.week1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(){
    private val contactFragment by lazy { ContactFragment() }
    private val galleryFragment by lazy { GalleryFragment() }
    private val currencyFragment by lazy { CurrencyFragment() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // initial fragment 설정
        supportFragmentManager.beginTransaction().add(R.id.fl_container, contactFragment).commit()

        // bottom navigation item 탭 이벤트 리스너 설정
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bnv_main)
        bottomNavigationView.run {
            setOnItemSelectedListener {
                when(it.itemId) {
                    R.id.contact_tab -> {
                        changeFragment(contactFragment)
                    }
                    R.id.gallery_tab -> {
                        changeFragment(galleryFragment)
                    }
                    R.id.free_tab -> {
                        changeFragment(currencyFragment)
                    }
                }
                true
            }
        }
    }

    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fl_container, fragment).commit()
    }
}