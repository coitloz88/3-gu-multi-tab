package com.example.week1.data

import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes

data class Contact(
    val id: Int,
    val name: String,
    val number: String,
    val image: Drawable?
)
