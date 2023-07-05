package com.example.week1.data

import kotlinx.serialization.Serializable

@Serializable
data class Contact(
    val id: Int,
    val name: String,
    val number: String,
    val imageUri: String?
)

/*
image는 uri만 저장하고,
drawable을 나중에 파싱하도록
 */