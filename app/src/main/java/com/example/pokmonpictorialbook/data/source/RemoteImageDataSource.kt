package com.example.pokmonpictorialbook.data.source

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL

class RemoteImageDataSource {
    suspend fun downloadImage(url: String): Bitmap {
        val urlConnection = withContext(Dispatchers.IO) {
            URL(url).openConnection()
        } as HttpURLConnection
        urlConnection.inputStream.use { inputStream ->
            return BitmapFactory.decodeStream(inputStream)
        }
    }
}