package com.example.week1.data

import androidx.annotation.DrawableRes

data class Contact(
    val id: Long,
    val name: String,
    @DrawableRes
    val image: Int?
)
