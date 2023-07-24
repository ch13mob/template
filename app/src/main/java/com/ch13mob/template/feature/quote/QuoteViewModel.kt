package com.ch13mob.template.feature.quote

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ch13mob.template.core.data.repository.QuoteRepository
import com.ch13mob.template.core.data.repository.UserDataRepository
import com.ch13mob.template.core.model.Quote
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuoteViewModel @Inject constructor(
    private val userDataRepository: UserDataRepository,
    private val quoteRepository: QuoteRepository
) : ViewModel() {

    private val _isLoadingState = MutableStateFlow(false)
    val isLoadingState = _isLoadingState.asStateFlow()

    private val _isErrorState = MutableStateFlow(false)
    val isErrorState = _isErrorState.asStateFlow()

    val quoteState: StateFlow<Quote> = quoteRepository.getQuote(id = 0)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = Quote()
        )

    val showDateState: StateFlow<Boolean> = userDataRepository.showDate
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = true
        )

    fun refreshQuote() {
        fetchQuote()
    }

    fun toggleShowDate() {
        viewModelScope.launch {
            userDataRepository.toggleShowDate()
        }
    }

    private fun fetchQuote() {
        viewModelScope.launch {
            _isLoadingState.update { true }
            quoteRepository.fetchQuote()
            _isLoadingState.update { false }
        }
    }
}
