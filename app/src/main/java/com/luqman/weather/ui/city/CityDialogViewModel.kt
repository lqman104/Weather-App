package com.luqman.weather.ui.city

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luqman.weather.data.repository.city.CityDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CityDialogViewModel @Inject constructor(
    private val cityDataSource: CityDataSource
) : ViewModel() {

    private val _state = MutableStateFlow(CityScreenState())
    val state = _state.asStateFlow()

    fun getCity() {
        viewModelScope.launch {
            cityDataSource.getAll().collect { response ->
                _state.value = _state.value.copy(
                    getDataState = response
                )
            }
        }
    }
}