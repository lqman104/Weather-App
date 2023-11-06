package com.luqman.weather.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.request.ImageRequest
import com.luqman.weather.R
import com.luqman.weather.core.model.asString
import com.luqman.weather.core.network.model.Resource
import com.luqman.weather.data.repository.model.Weather
import com.luqman.weather.ui.model.WeatherGroup
import com.luqman.weather.uikit.component.ImageComponent
import com.luqman.weather.uikit.component.LoadingComponent
import com.luqman.weather.uikit.component.MessageScreenComponent
import com.luqman.weather.uikit.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.luqman.weather.core.R as CoreR

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                MainScreen(modifier = Modifier.fillMaxSize())
            }
        }
    }
}

@Composable
fun MainScreen(
    modifier: Modifier = Modifier
) {
    val viewModel: MainViewModel = viewModel()
    var query by remember { mutableStateOf("") }
    var job by remember { mutableStateOf<Job?>(null) }

    LaunchedEffect(query) {
        // Cancel the previous job if a new character is typed within the debounce time
        job?.cancel()
        job = launch {
            delay(800)
            viewModel.search(query)
        }
    }

    val state by viewModel.state.collectAsState()

    Scaffold(
        modifier = modifier
    ) { paddingValues ->
        Column(modifier = modifier.padding(16.dp)) {
            Row {
                TextField(
                    modifier = Modifier.weight(1f),
                    enabled = state.getDataState !is Resource.Loading,
                    value = query,
                    onValueChange = { query = it }
                )

                IconButton(onClick = {  }) {

                }
            }

            when (state.getDataState) {
                is Resource.Success -> {
                    MainContent(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues),
                        allForecast = state.allForecast,
                        todayForecast = state.todayForecast
                    )
                }

                is Resource.Loading -> {
                    LoadingComponent(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues),
                    )
                }

                is Resource.Error -> {
                    MessageScreenComponent(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues),
                        title = stringResource(id = CoreR.string.error_title),
                        message = state.getDataState?.error?.asString().orEmpty(),
                        showActionButton = true,
                        actionButtonText = stringResource(id = CoreR.string.error_button),
                        onActionButtonClicked = viewModel::refresh
                    )
                }

                else -> {
                    MessageScreenComponent(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues),
                        title = stringResource(id = R.string.input_city_name),
                        message = stringResource(id = R.string.input_city_name_instruction),
                        showActionButton = false
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainContent(
    modifier: Modifier = Modifier,
    todayForecast: Weather? = null,
    allForecast: List<WeatherGroup>? = null,
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(vertical = 16.dp),
        content = {
            if (todayForecast != null) {
                item {
                    WeatherCard(
                        modifier = Modifier.fillMaxWidth(),
                        weather = todayForecast
                    )
                }
            }

            if (!allForecast.isNullOrEmpty()) {
                item {
                    Spacer(modifier = Modifier.height(32.dp))
                    Text(
                        text = stringResource(id = R.string.all_four_days_forecast),
                        style = MaterialTheme.typography.headlineSmall
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }

                allForecast.forEach { group ->
                    stickyHeader {
                        DateCard(value = group.date)
                    }
                    items(group.forecast) {
                        WeatherSmallCard(
                            modifier = modifier
                                .padding(vertical = 8.dp)
                                .fillMaxWidth(),
                            weather = it
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            } else {
                item {
                    MessageScreenComponent(
                        title = stringResource(id = R.string.data_not_found),
                        message = stringResource(id = R.string.data_not_found_description),
                        showActionButton = false,
                    )
                }
            }
        }
    )
}

@Composable
fun DateCard(
    modifier: Modifier = Modifier,
    value: String
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background),
    ) {
        Text(
            text = value,
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            style = MaterialTheme.typography.titleMedium,
        )
    }
}

@Composable
fun WeatherCard(
    modifier: Modifier = Modifier,
    weather: Weather
) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(Modifier.height(40.dp))
        ImageComponent(
            model = ImageRequest.Builder(LocalContext.current)
                .data("https://openweathermap.org/img/wn/${weather.icon}@2x.png")
                .crossfade(true)
                .build(),
            modifier = Modifier.size(130.dp)
        )
        Text(
            text = weather.temp.toString() + "\u2103",
            style = MaterialTheme.typography.displayMedium,
            modifier = Modifier.padding(horizontal = 16.dp),
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = "${weather.city} - ${weather.weather}",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(horizontal = 16.dp),
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = weather.description,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(horizontal = 16.dp),
        )
        Spacer(Modifier.height(16.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            SmallInfoCard(
                modifier = Modifier
                    .padding(4.dp)
                    .weight(1f),
                title = stringResource(R.string.wind_gust_label),
                value = weather.windGust.toString() + " " + stringResource(id = R.string.speed_suffix),
            )
            SmallInfoCard(
                modifier = Modifier
                    .padding(4.dp)
                    .weight(1f),
                title = stringResource(R.string.wind_speed_label),
                value = weather.windSpeed.toString() + " " + stringResource(id = R.string.speed_suffix),
            )
        }
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            SmallInfoCard(
                modifier = Modifier
                    .padding(4.dp)
                    .weight(1f),
                title = stringResource(R.string.wind_degree_label),
                value = weather.windDeg.toString(),
            )
            SmallInfoCard(
                modifier = Modifier
                    .padding(4.dp)
                    .weight(1f),
                title = stringResource(R.string.humidity_label),
                value = weather.humidity.toString(),
            )
        }
    }
}

@Composable
fun WeatherSmallCard(
    modifier: Modifier = Modifier,
    weather: Weather
) {
    var expand by remember {
        mutableStateOf(false)
    }
    Card {
        Row(modifier = modifier) {
            Spacer(Modifier.width(16.dp))
            ImageComponent(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("https://openweathermap.org/img/wn/${weather.icon}@2x.png")
                    .crossfade(true)
                    .build(),
                modifier = Modifier.size(70.dp),
            )
            Spacer(Modifier.width(16.dp))
            Column(Modifier.weight(1f)) {
                Text(
                    text = weather.time,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(2.dp))
                Text(
                    text = "${weather.temp}\u2103 - ${weather.weather}",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = weather.description,
                    style = MaterialTheme.typography.bodySmall,
                )

                AnimatedVisibility(visible = expand) {
                    Column {
                        Spacer(Modifier.height(8.dp))
                        SmallInfoCardHorizontal(
                            title = stringResource(R.string.wind_gust_label),
                            value = weather.windGust.toString() + " " + stringResource(id = R.string.speed_suffix),
                        )

                        Spacer(Modifier.height(4.dp))
                        SmallInfoCardHorizontal(
                            title = stringResource(R.string.wind_speed_label),
                            value = weather.windSpeed.toString() + " " + stringResource(id = R.string.speed_suffix),
                        )

                        Spacer(Modifier.height(4.dp))
                        SmallInfoCardHorizontal(
                            title = stringResource(R.string.wind_degree_label),
                            value = weather.windDeg.toString(),
                        )

                        Spacer(Modifier.height(4.dp))
                        SmallInfoCardHorizontal(
                            title = stringResource(R.string.humidity_label),
                            value = weather.humidity.toString(),
                        )
                    }
                }
            }
            IconButton(
                onClick = { expand = !expand },
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                Icon(
                    imageVector = if (expand) {
                        Icons.Default.KeyboardArrowUp
                    } else {
                        Icons.Default.KeyboardArrowDown
                    },
                    contentDescription = null
                )
            }
        }
    }
}

@Composable
fun SmallInfoCard(
    modifier: Modifier = Modifier,
    title: String,
    value: String
) {
    Card(modifier = modifier) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.titleMedium,
            )
        }
    }
}

@Composable
fun SmallInfoCardHorizontal(
    modifier: Modifier = Modifier,
    title: String,
    value: String
) {
    Row(
        modifier = modifier,
    ) {
        Text(
            text = "$title : ",
            style = MaterialTheme.typography.bodyMedium,
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    AppTheme {
        Surface {
            MainContent(
                modifier = Modifier.fillMaxSize(),
                allForecast = listOf(),
            )
        }
    }
}