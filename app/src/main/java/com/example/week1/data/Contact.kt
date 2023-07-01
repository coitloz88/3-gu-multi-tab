package com.example.week1.data

import androidx.annotation.DrawableRes

data class Contact(
    val id: Int,
    val name: String,
    val number: String,
    @DrawableRes
    val image: Int?
)
