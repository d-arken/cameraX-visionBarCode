package com.vuxx.camerax.Image

import android.util.Log
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetectorOptions
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.common.FirebaseVisionImageMetadata
import com.vuxx.camerax.ViewModel.BarcodeReaderViewModel

class BarcodeScanningAnalyzer(private val barcodeReaderViewModel: BarcodeReaderViewModel) : ImageAnalysis.Analyzer {




    private fun degreesToFirebaseRotation(degrees: Int): Int = when(degrees) {
        0 -> FirebaseVisionImageMetadata.ROTATION_0
        90 -> FirebaseVisionImageMetadata.ROTATION_90
        180 -> FirebaseVisionImageMetadata.ROTATION_180
        270 -> FirebaseVisionImageMetadata.ROTATION_270
        else -> throw Exception("Rotation must be 0, 90, 180, or 270.")
    }

    var times = 0



    override fun analyze(image: ImageProxy?, rotationDegrees: Int) {
        val mediaImage = image?.image
        val imageRotation = degreesToFirebaseRotation(rotationDegrees)
        if(mediaImage != null) {
            val image = FirebaseVisionImage.fromMediaImage(mediaImage, imageRotation)
            val options = FirebaseVisionBarcodeDetectorOptions.Builder()
                .setBarcodeFormats(
                    FirebaseVisionBarcode.FORMAT_CODE_128)
                .build()

            val detector = FirebaseVision
                .getInstance()
                .getVisionBarcodeDetector(options)


            detector.detectInImage(image)
                .addOnSuccessListener { barcodes ->
                    times += 1

                    for(barcode in barcodes){
                       Log.d("Log 2", barcode.rawValue)
                       barcodeReaderViewModel.barcode.value = barcode.rawValue+"Vezes"+times

                    }


                }
                .addOnFailureListener {
                    Log.d("Barcodes","Erro")
                }

        }

    }

}