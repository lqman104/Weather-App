package com.luqman.weather.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luqman.weather.core.helper.DateHelper
import com.luqman.weather.core.helper.DateHelper.getIntHours
import com.luqman.weather.core.network.model.Resource
import com.luqman.weather.data.repository.WeatherDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val weatherDataSource: WeatherDataSource
) : ViewModel() {

    private val _state = MutableStateFlow(MainScreenState())
    val state = _state.asStateFlow()

    @OptIn(FlowPreview::class)
    fun search(city: String) {
        _state.value = _state.value.copy(
            query = city.ifEmpty { null }
        )
        viewModelScope.launch {
            weatherDataSource.search(city).debounce(800).collect { response ->
                _state.value = _state.value.copy(
                    getDataState = response
                )

                if (response is Resource.Success) {
                    val endTime = DateHelper.currentTime(toFormat = DateHelper.HOUR)
                    // get all today forecast
                    val todayForecast = response.data?.filter { it.date == DateHelper.currentDate() }
                    // filtering the last time data comparing with current time
                    val currentTimeForecast = todayForecast?.lastOrNull { it.time.getIntHours() < endTime.toInt() }


                    _state.value = _state.value.copy(
                        todayForecast = currentTimeForecast,
                        allForecast = response.data.orEmpty()
                    )
                }
            }
        }
    }

    fun refresh() {
        search(_state.value.query.orEmpty())
    }
}