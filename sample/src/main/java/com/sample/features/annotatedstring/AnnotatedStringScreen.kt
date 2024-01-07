package com.sample.features.annotatedstring

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.sample.features.annotatedstring.component.HashtagsText
import com.sample.features.annotatedstring.component.SubscriptText
import com.sample.features.annotatedstring.component.SuperscriptText
import com.sample.features.annotatedstring.component.TermsAndConditionsText

@Composable
fun AnnotatedStringScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val context = LocalContext.current

        TermsAndConditionsText(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            fullText = MockData.PrivacyPolicy.fullText,
            links = MockData.PrivacyPolicy.links,
        )
        Spacer(modifier = Modifier.height(24.dp))
        HashtagsText(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            text = MockData.Hashtags.text,
            onTagClick = { tag ->
                Toast.makeText(context, "$tag clicked.", Toast.LENGTH_SHORT).show()
            }
        )
        Spacer(modifier = Modifier.height(24.dp))
        SuperscriptText(
            normalText = "X",
            superText = "2"
        )
        Spacer(modifier = Modifier.height(24.dp))
        SubscriptText(
            normalText = "O",
            subText = "2"
        )
    }
}
