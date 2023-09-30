package com.ch13mob.sample.features.annotatedstring

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ch13mob.sample.features.annotatedstring.component.TermsAndConditionsText

@Composable
fun AnnotatedStringScreen() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TermsAndConditionsText(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            fullText = MockData.PrivacyPolicy.fullText,
            links = MockData.PrivacyPolicy.links,
        )
    }
}
