package com.branovitski.a3205testapp.ui.search

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Environment
import androidx.core.app.ActivityCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class Downloader(
    private val applicationContext: Context,
    private val responseBody: ResponseBody,
    private val fileName: String,
    private val onComplete: () -> Unit,
) {

    private suspend fun downloading() {
        withContext(Dispatchers.IO) {
            try {
                val downloadsFolder =
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                val outputFile = File(downloadsFolder, "$fileName.zip")

                var outputStream: FileOutputStream? = null
                try {
                    val inputStream = responseBody.byteStream()
                    outputStream = FileOutputStream(outputFile)
                    val buffer = ByteArray(4096)
                    var bytesRead: Int
                    while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                        outputStream.write(buffer, 0, bytesRead)
                    }
                    outputStream.flush()
                    onComplete()
                } catch (e: IOException) {
                    e.printStackTrace()
                } finally {
                    outputStream?.close()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    suspend fun downloadFile() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            downloading()
        } else {
            if (checkPermission()) {
                downloading()
            } else {
                requestPermission()
            }
        }
    }

    private fun checkPermission(): Boolean {
        return ActivityCompat.checkSelfPermission(
            applicationContext, Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission() {
        if (!ActivityCompat.shouldShowRequestPermissionRationale(
                applicationContext as Activity, Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        ) {
            ActivityCompat.requestPermissions(
                applicationContext, arrayOf(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ), 42
            )
        }
    }
}