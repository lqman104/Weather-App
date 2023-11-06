package com.luqman.weather.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luqman.weather.core.helper.DateHelper
import com.luqman.weather.core.helper.DateHelper.getIntHours
import com.luqman.weather.core.network.model.Resource
import com.luqman.weather.data.repository.city.CityDataSource
import com.luqman.weather.data.repository.model.City
import com.luqman.weather.data.repository.weather.WeatherDataSource
import com.luqman.weather.ui.model.WeatherGroup
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val weatherDataSource: WeatherDataSource,
    private val cityDataSource: CityDataSource
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
                    val getCity = cityDataSource.get(city)
                    var isFavoriteCity = false
                    var cityId = 0

                    if (getCity is Resource.Success) {
                        isFavoriteCity = true
                        cityId = getCity.data?.id ?: 0
                    }

                    val endTime = DateHelper.currentTime(toFormat = DateHelper.HOUR)
                    // get all today forecast
                    val todayForecast =
                        response.data?.filter { it.date == DateHelper.currentDate() }
                    // filtering the last time data comparing with current time
                    val currentTimeForecast =
                        todayForecast?.lastOrNull { it.time.getIntHours() < endTime.toInt() }
                    // grouping
                    var date = ""
                    val parent = mutableListOf<WeatherGroup>()
                    response.data.orEmpty().forEach {
                        if (date != it.date) {
                            date = it.date
                            parent.add(
                                WeatherGroup(date, mutableListOf(it))
                            )
                        } else {
                            parent.lastOrNull()?.forecast?.add(it)
                        }
                    }

                    _state.value = _state.value.copy(
                        todayForecast = currentTimeForecast,
                        allForecast = parent,
                        isFavoriteCity = isFavoriteCity,
                        cityId = cityId
                    )
                }
            }
        }
    }

    fun refresh() {
        search(_state.value.query.orEmpty())
    }

    fun saveCity() {
        viewModelScope.launch {
            val insertedId = cityDataSource.save(
                City(id = 0, name = _state.value.query.orEmpty())
            )
            _state.value = _state.value.copy(
                isFavoriteCity = true,
                cityId = insertedId.toInt()
            )
        }
    }

    fun deleteCity() {
        viewModelScope.launch {
            cityDataSource.delete(_state.value.cityId)
            _state.value = _state.value.copy(
                isFavoriteCity = false,
                cityId = 0
            )
        }
    }
}