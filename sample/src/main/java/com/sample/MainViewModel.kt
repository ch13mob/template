package com.sample

import androidx.lifecycle.ViewModel
import com.sample.features.Feature
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(MainUiState(Feature.entries))
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()
}

data class MainUiState(
    val features: List<Feature> = emptyList()
)
