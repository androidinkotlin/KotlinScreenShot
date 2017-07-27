package com.cs.imran.kotlinscreenshot

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import java.io.File
import java.io.FileOutputStream




class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    public fun checkPermissions(view: View) {
        if (Build.VERSION.SDK_INT >= 23) {
            // Marshmallow+

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                // Check Permissions Now

                ActivityCompat.requestPermissions(this, arrayOf<String>(Manifest.permission.WRITE_EXTERNAL_STORAGE), 45)
            } else {
                // permission has been granted, continue as usual
                captureScreenshot();
            }

        } else {
            // Pre-Marshmallow
            captureScreenshot();
        }
    }
    private fun captureScreenshot() {
        try {
            // image saving sd card path
            val mPath = Environment.getExternalStorageDirectory().toString() + "/" + System.currentTimeMillis() + ".jpg"

            // create bitmap screen capture
            val view = window.decorView.rootView
            view.isDrawingCacheEnabled = true

            val bitmap = Bitmap.createBitmap(view.drawingCache)
            view.isDrawingCacheEnabled = false

            val imageFile = File(mPath)
            val outputStream = FileOutputStream(imageFile)

            val quality = 100
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
            outputStream.flush()
            outputStream.close()

        } catch (e: Throwable) {
            e.printStackTrace()
        }

    }
}
