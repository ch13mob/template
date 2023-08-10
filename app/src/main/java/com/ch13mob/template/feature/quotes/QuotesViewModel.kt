package com.ch13mob.template.feature.quotes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ch13mob.template.core.common.exception.message
import com.ch13mob.template.core.data.repository.QuoteRepository
import com.ch13mob.template.core.model.Quote
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuotesViewModel @Inject constructor(
    private val quoteRepository: QuoteRepository
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    private val _errorMessage = MutableStateFlow<String?>(null)
    private val _quotesStream =
        quoteRepository.getQuotesStream().catch { exception ->
            _errorMessage.update { exception.message() }
            _isLoading.update { false }
        }

    val uiState: StateFlow<QuotesUiState> = combine(
        _quotesStream,
        _isLoading,
        _errorMessage
    ) { quotes, isLoading, errorMessage ->
        QuotesUiState(
            quotes = quotes,
            isLoading = isLoading,
            errorMessage = errorMessage
        )
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = QuotesUiState(isLoading = true)
        )

    fun onEvent(event: QuotesUiEvent) {
        when (event) {
            QuotesUiEvent.RefreshQuotes -> fetchQuotes()
            QuotesUiEvent.ErrorConsumed -> onErrorConsumed()
        }
    }

    private fun onErrorConsumed() {
        _errorMessage.update { null }
    }

    private fun fetchQuotes() {
        viewModelScope.launch {
            runCatching {
                _isLoading.update { true }
                quoteRepository.fetchQuotes()
                _isLoading.update { false }
            }.onFailure { exception ->
                _errorMessage.update { exception.message() }
                _isLoading.update { false }
            }
        }
    }
}

sealed class QuotesUiEvent {
    object RefreshQuotes : QuotesUiEvent()
    object ErrorConsumed : QuotesUiEvent()
}

data class QuotesUiState(
    val quotes: List<Quote> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
