package com.ch13mob.template.feature.qoutedetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.ch13mob.template.feature.qoutedetail.navigation.QuoteIdArg
import com.ch13mob.template.feature.qoutedetail.navigation.QuoteTextArg
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class QuoteDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val quoteId: Int = savedStateHandle[QuoteIdArg] ?: -1
    val quoteText: String = savedStateHandle[QuoteTextArg] ?: ""
}
