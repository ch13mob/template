package com.ch13mob.sample

import androidx.lifecycle.ViewModel
import com.ch13mob.sample.features.Feature
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(MainUiState(Feature.values().asList()))
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()
}

data class MainUiState(
    val features: List<Feature> = emptyList()
)
