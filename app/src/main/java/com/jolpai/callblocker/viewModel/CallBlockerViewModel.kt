package com.jolpai.callblocker.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jolpai.callblocker.db.BlockedNumberRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CallBlockerViewModel @Inject constructor(
    private val repository: BlockedNumberRepository
): ViewModel() {

    val blockedNumbers = repository.getBlockedNumbers().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun blockNumber(number: String) {
        viewModelScope.launch {
            repository.insertBlockedNumber(number)
        }
    }
}