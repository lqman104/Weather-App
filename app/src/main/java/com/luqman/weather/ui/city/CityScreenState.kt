package com.luqman.weather.ui.city

import com.luqman.weather.core.network.model.Resource
import com.luqman.weather.data.repository.model.City

data class CityScreenState(
    val getDataState: Resource<List<City>> = Resource.Loading(),
)