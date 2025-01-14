package com.example.pokmonpictorialbook.data.source

import android.content.Context
import android.graphics.Bitmap
import android.os.Environment
import java.io.File
import java.io.FileOutputStream

class LocalImageDataSource(
    private val context: Context
) {
    fun getLocalImageFile(imageName: String): File? {
        val file = File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "$imageName.png")
        return if(file.exists()) {
            file
        } else {
            null
        }
    }

    fun saveImageToInternalStorage(imageName: String, bitmap: Bitmap): File {
        // 外部ストレージのアプリ固有ディレクトリに保存する。
        val file = File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "$imageName.png")
        FileOutputStream(file).use { outputSteam ->
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputSteam)
        }
        return file
    }
}