package com.sample.features.scanner

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.google.mlkit.vision.documentscanner.GmsDocumentScanner
import com.google.mlkit.vision.documentscanner.GmsDocumentScannerOptions
import com.google.mlkit.vision.documentscanner.GmsDocumentScannerOptions.RESULT_FORMAT_JPEG
import com.google.mlkit.vision.documentscanner.GmsDocumentScannerOptions.RESULT_FORMAT_PDF
import com.google.mlkit.vision.documentscanner.GmsDocumentScannerOptions.SCANNER_MODE_FULL
import com.google.mlkit.vision.documentscanner.GmsDocumentScanning
import com.google.mlkit.vision.documentscanner.GmsDocumentScanningResult
import java.io.File
import java.io.FileOutputStream

@Suppress("LongMethod", "MagicNumber")
@Composable
fun DocumentScannerScreen() {
    val context = LocalContext.current
    var scanner by remember { mutableStateOf<GmsDocumentScanner?>(null) }
    var imageUris by remember { mutableStateOf<List<Uri>>(emptyList()) }
    val scannerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult(),
        onResult = { activityResult ->
            if (activityResult.resultCode == Activity.RESULT_OK) {
                val result = GmsDocumentScanningResult.fromActivityResultIntent(activityResult.data)
                imageUris = result?.pages?.map { it.imageUri } ?: emptyList()

                // save PDF to internal storage
                result?.pdf?.let { pdf ->
                    val fileOutputStream = FileOutputStream(File(context.filesDir, "document.pdf"))
                    context.contentResolver.openInputStream(pdf.uri)?.use { input ->
                        input.copyTo(fileOutputStream)
                    }
                }
            }
        }
    )

    LaunchedEffect(Unit) {
        val options = GmsDocumentScannerOptions.Builder()
            .setScannerMode(SCANNER_MODE_FULL)
            .setGalleryImportAllowed(true)
            .setPageLimit(3)
            .setResultFormats(RESULT_FORMAT_PDF, RESULT_FORMAT_JPEG)
            .build()

        scanner = GmsDocumentScanning.getClient(options)
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
        ) {
            items(imageUris) { uri ->
                AsyncImage(
                    modifier = Modifier.fillMaxWidth(),
                    model = uri,
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth
                )
            }
        }

        Button(
            modifier = Modifier
                .padding(vertical = 24.dp)
                .align(Alignment.BottomCenter),
            onClick = {
                scanner?.let {
                    it.getStartScanIntent(context.findActivity())
                        .addOnSuccessListener { intentSender ->
                            scannerLauncher.launch(
                                IntentSenderRequest.Builder(intentSender).build()
                            )
                        }
                        .addOnFailureListener { exception ->
                            Toast.makeText(context, exception.message, Toast.LENGTH_SHORT)
                                .show()
                        }
                }
            }
        ) {
            Text(text = "Scan Document")
        }
    }
}

@Suppress("UseCheckOrError")
private fun Context.findActivity(): Activity {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) return context
        context = context.baseContext
    }
    throw IllegalStateException("Permissions should be called in the context of an Activity")
}
