package com.sample.features.photopicker

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

/**
 *
 * [Photo picker](https://developer.android.com/training/data-storage/shared/photopicker)
 *
 */

@Suppress("LongMethod")
@Composable
fun PhotoPickerScreen() {
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var imageUris by remember { mutableStateOf<List<Uri>>(emptyList()) }

    val pickMedia = rememberLauncherForActivityResult(
        contract = PickVisualMedia(),
        onResult = { uri ->
            if (uri != null) {
                imageUri = uri
                imageUris = emptyList()
            }
        }
    )
    val pickMultipleMedia = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(maxItems = 3),
        onResult = { uris ->
            if (uris.isNotEmpty()) {
                imageUris = uris
                imageUri = null
            }
        }
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        LazyRow(
            modifier = Modifier.height(200.dp),
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (imageUri != null) {
                item(imageUri) {
                    AsyncImage(
                        modifier = Modifier.size(200.dp),
                        model = imageUri,
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )
                }
            }
            items(imageUris) { uri ->
                AsyncImage(
                    modifier = Modifier.size(200.dp),
                    model = uri,
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            pickMedia.launch(PickVisualMediaRequest(PickVisualMedia.ImageOnly))
        }) {
            Text(text = "Pick one photo")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = {
            pickMultipleMedia.launch(PickVisualMediaRequest(PickVisualMedia.ImageOnly))
        }) {
            Text(text = "Pick multiple photos")
        }
    }
}
