package com.example.fiyat_radar

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.OptIn
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class KameraActivity : AppCompatActivity() {

    private lateinit var previewView: PreviewView
    private lateinit var cameraExecutor: ExecutorService

    companion object {
        private const val TAG = "KameraActivity"
        var no: String = "" // Barkod değeri burada saklanacak
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate çağrıldı")
        setContentView(R.layout.activity_kamera)

        previewView = findViewById(R.id.previewView)
        cameraExecutor = Executors.newSingleThreadExecutor()

        requestCameraPermission()
    }

    // Kamera izni kontrol etme ve izni alındığında kamera başlatma
    private fun requestCameraPermission() {
        if (allPermissionsGranted()) {
            Log.d(TAG, "Kamera izni verildi, kamerayı başlatıyoruz...")
            startCamera()
        } else {
            Log.d(TAG, "Kamera izni verilmedi, izin isteniyor...")
            permissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            Log.d(TAG, "Kamera izni verildi.")
            startCamera()
        } else {
            Log.d(TAG, "Kamera izni verilmedi.")
            Toast.makeText(this, "Kamera izni verilmedi", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun allPermissionsGranted() = ContextCompat.checkSelfPermission(
        this, Manifest.permission.CAMERA
    ) == PackageManager.PERMISSION_GRANTED

    // Kamera başlatma işlemi
    private fun startCamera() {
        Log.d(TAG, "Kamera başlatılıyor...")

        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        // Kamera provider alınmaya çalışıyor, işlemi ekleyelim
        cameraProviderFuture.addListener({
            try {
                val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
                Log.d(TAG, "Kamera provider başarıyla alındı.")

                val preview = Preview.Builder()
                    .build()
                    .also {
                        it.setSurfaceProvider(previewView.surfaceProvider)
                    }

                val barcodeScanner = BarcodeScanning.getClient()

                val imageAnalysis = ImageAnalysis.Builder()
                    .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                    .build()

                imageAnalysis.setAnalyzer(cameraExecutor) { imageProxy ->
                    processImageProxy(barcodeScanner, imageProxy)
                }

                val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

                // Kamerayı bağlama
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    this@KameraActivity, cameraSelector, preview, imageAnalysis
                )

                Log.d(TAG, "Kamera başarıyla başlatıldı.")

            } catch (exc: Exception) {
                Log.e(TAG, "Kamera başlatılamadı: ${exc.localizedMessage}", exc)
                Toast.makeText(this, "Kamera başlatılamadı.", Toast.LENGTH_SHORT).show()
            }
        }, ContextCompat.getMainExecutor(this))

        // Eğer işlem başlatılmadıysa, burada log ekleyelim
        cameraProviderFuture.addListener({
            if (!cameraProviderFuture.isDone) {
                Log.e(TAG, "Kamera provider alınamadı.")
            }
        }, ContextCompat.getMainExecutor(this))
    }

    @OptIn(ExperimentalGetImage::class)
    private fun processImageProxy(
        scanner: com.google.mlkit.vision.barcode.BarcodeScanner,
        imageProxy: ImageProxy
    ) {
        Log.d(TAG, "Barkod tarama işlemi başlatıldı.")

        val mediaImage = imageProxy.image
        if (mediaImage != null) {
            val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)

            // Gecikme ekleme (2 saniye bekleme)
            val handler = Handler(mainLooper)
            handler.postDelayed({
                scanner.process(image)
                    .addOnSuccessListener { barcodes ->
                        if (barcodes.isEmpty()) {
                            Log.d(TAG, "Barkod bulunamadı.")
                        }
                        for (barcode in barcodes) {
                            barcode.rawValue?.let { value ->
                                Log.d(TAG, "Barkod bulundu: $value")
                                BarcodeData.no = value // Barkod değerini BarcodeData'ya atıyoruz

                                // Barkod okunduğunda UrunGetir ekranına geçiş
                                val intent = Intent(this@KameraActivity, UrunGetir::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                                startActivity(intent)
                                finish() // Kamera ekranını kapatıyoruz
                            }
                        }
                    }
                    .addOnFailureListener { e ->
                        Log.e(TAG, "Barkod tarama hatası: ${e.localizedMessage}", e)
                    }
                    .addOnCompleteListener {
                        imageProxy.close() // Resim işleme tamamlandıktan sonra proxy'i kapat
                    }
            }, 2000) // 2 saniye bekleme

        } else {
            Log.e(TAG, "Media image null, barkod taraması yapılamadı.")
            imageProxy.close() // Eğer media image null ise proxy'i kapat
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown() // Kamera işlemcisini düzgün bir şekilde kapat
    }
}
