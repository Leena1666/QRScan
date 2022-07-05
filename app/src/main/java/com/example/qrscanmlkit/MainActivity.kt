package com.example.qrscanmlkit

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetectorOptions
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import java.time.Duration

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val image = FirebaseVisionImage.fromBitmap(
            BitmapFactory.decodeResource(
                this.getResources(),
                R.drawable.myqr
            )
        )

        val options = FirebaseVisionBarcodeDetectorOptions.Builder()
            .setBarcodeFormats(
                FirebaseVisionBarcode.FORMAT_ALL_FORMATS,
            )
            .build()
        val myDetector = FirebaseVision.getInstance().getVisionBarcodeDetector(options)


        myDetector.detectInImage(image)
            .addOnSuccessListener {
                Toast.makeText(this, "Success:: " , Toast.LENGTH_SHORT)
                    .show()


                for (barcode in it) {
                    val bounds = barcode.boundingBox
                    val corners = barcode.cornerPoints

                    val rawValue = barcode.rawValue

                    val valueType = barcode.valueType

                    // See API reference for complete list of supported types
                    when (valueType) {
                        FirebaseVisionBarcode.TYPE_WIFI -> {
                            val ssid = barcode.wifi!!.ssid
                            val password = barcode.wifi!!.password
                            val type = barcode.wifi!!.encryptionType
                        }
                        FirebaseVisionBarcode.TYPE_URL -> {
                            val title = barcode.url!!.title
                            val url = barcode.url!!.url
                        }
                    }
                }


                    // Task completed successfully

            }
            .addOnFailureListener {
                Toast.makeText(this, "Fail", Toast.LENGTH_SHORT).show()

                // Task failed with an exception
            }

    }
}