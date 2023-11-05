package com.luqman.weather.data.services.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WeatherHttpResponse(

	@Json(name="city")
	val city: City? = null,

	@Json(name="cnt")
	val cnt: Int? = null,

	@Json(name="cod")
	val cod: String? = null,

	@Json(name="message")
	val message: Int? = null,

	@Json(name="list")
	val list: List<ListItem>? = null
)

@JsonClass(generateAdapter = true)
data class Coordinate(

	@Json(name="lon")
	val lon: String? = null,

	@Json(name="lat")
	val lat: String? = null
)

@JsonClass(generateAdapter = true)
data class Wind(

	@Json(name="deg")
	val deg: Int? = null,

	@Json(name="speed")
	val speed: Double? = null,

	@Json(name="gust")
	val gust: Double? = null
)

@JsonClass(generateAdapter = true)
data class ListItem(

	@Json(name="dt")
	val dt: Int? = null,

	@Json(name="pop")
	val pop: Double? = null,

	@Json(name="rain")
	val rain: Rain? = null,

	@Json(name="visibility")
	val visibility: Int? = null,

	@Json(name="dt_txt")
	val dtTxt: String? = null,

	@Json(name="weather")
	val weather: List<WeatherItem>? = null,

	@Json(name="main")
	val main: Main? = null,

	@Json(name="clouds")
	val clouds: Clouds? = null,

	@Json(name="sys")
	val sys: Sys? = null,

	@Json(name="wind")
	val wind: Wind? = null
)

@JsonClass(generateAdapter = true)
data class WeatherItem(

	@Json(name="icon")
	val icon: String? = null,

	@Json(name="description")
	val description: String? = null,

	@Json(name="main")
	val main: String? = null,

	@Json(name="id")
	val id: Int? = null
)

@JsonClass(generateAdapter = true)
data class Clouds(

	@Json(name="all")
	val all: Int? = null
)

@JsonClass(generateAdapter = true)
data class Main(

	@Json(name="temp")
	val temp: Double? = null,

	@Json(name="temp_min")
	val tempMin: Double? = null,

	@Json(name="grnd_level")
	val grndLevel: Int? = null,

	@Json(name="temp_kf")
	val tempKf: Double? = null,

	@Json(name="humidity")
	val humidity: Int? = null,

	@Json(name="pressure")
	val pressure: Int? = null,

	@Json(name="sea_level")
	val seaLevel: Int? = null,

	@Json(name="feels_like")
	val feelsLike: Double? = null,

	@Json(name="temp_max")
	val tempMax: Double? = null
)

@JsonClass(generateAdapter = true)
data class Rain(

	@Json(name="3h")
	val jsonMember3h: Double? = null
)

@JsonClass(generateAdapter = true)
data class City(

	@Json(name="country")
	val country: String? = null,

	@Json(name="coord")
	val coordinate: Coordinate? = null,

	@Json(name="sunrise")
	val sunrise: Int? = null,

	@Json(name="timezone")
	val timezone: Int? = null,

	@Json(name="sunset")
	val sunset: Int? = null,

	@Json(name="name")
	val name: String? = null,

	@Json(name="id")
	val id: Int? = null,

	@Json(name="population")
	val population: Int? = null
)

@JsonClass(generateAdapter = true)
data class Sys(

	@Json(name="pod")
	val pod: String? = null
)
