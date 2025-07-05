package com.example.parkeador.features.buscarhuella

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class BuscarHuellaViewModel : ViewModel() {

    private val _isSearching = MutableStateFlow(false)
    val isSearching: StateFlow<Boolean> = _isSearching

    fun onStartSearchClick() {
        _isSearching.value = true
    }
}