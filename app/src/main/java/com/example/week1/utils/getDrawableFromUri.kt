package com.example.week1.utils

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import java.io.IOException

fun getDrawableFromUri(activity: Activity, uri: String): Drawable? {
    try {
        lateinit var imageBp: Bitmap
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
            imageBp = ImageDecoder.decodeBitmap(ImageDecoder.createSource(activity.contentResolver, Uri.parse(uri)))
        } else {
            imageBp = MediaStore.Images.Media.getBitmap(activity.contentResolver, Uri.parse(uri))
        }
        return BitmapDrawable(imageBp)
    } catch (e: IOException) {
        Log.e("ContactFragment", e.toString())
    }
    return null
}