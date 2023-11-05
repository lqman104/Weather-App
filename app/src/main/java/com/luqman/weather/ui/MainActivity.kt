package com.luqman.weather.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.luqman.weather.core.network.model.Resource
import com.luqman.weather.data.repository.model.Weather
import com.luqman.weather.uikit.component.LoadingComponent
import com.luqman.weather.uikit.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                // A surface container using the 'background' color from the theme
                Surface {
                    MainScreen(modifier = Modifier.fillMaxSize())
                }
            }
        }
    }
}

@Composable
fun MainScreen(
    modifier: Modifier = Modifier
) {
    val viewModel: MainViewModel = viewModel()

    LaunchedEffect(Unit) {
        viewModel.search("bekasi")
    }

    val state by viewModel.state.collectAsState()

    if (state.getDataState is Resource.Success) {
        MainContent(
            modifier = modifier.fillMaxSize(),
            allForecast = state.allForecast,
            todayForecast = state.todayForecast,
            onQueryChange = viewModel::search
        )
    } else {
        LoadingComponent(
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun MainContent(
    modifier: Modifier = Modifier,
    todayForecast: Weather? = null,
    allForecast: List<Weather>? = null,
    onQueryChange: (String) -> Unit,
) {
    var query by remember {
        mutableStateOf("")
    }

    Column(modifier = modifier.padding(16.dp)) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = query,
            onValueChange = {
                onQueryChange(it)
                query = it
            }
        )

        if (todayForecast != null) {
            WeatherCard(
                modifier = Modifier.fillMaxWidth(),
                weather = todayForecast
            )
        }

        LazyColumn(
            contentPadding = PaddingValues(vertical = 16.dp),
            content = {
                items(allForecast.orEmpty()) {
                    Card(
                        shape = MaterialTheme.shapes.small,
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = it.weather,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            })
    }
}

@Composable
fun WeatherCard(
    modifier: Modifier = Modifier,
    weather: Weather,

    ) {
    Card(
        modifier = modifier,
    ) {
        Text(
            text = "Suhu:" + weather.temp.toString(),
            modifier = Modifier.padding(16.dp)
        )
        Text(
            text = "Seperti:" + weather.tempFeelsLike.toString(),
            modifier = Modifier.padding(16.dp)
        )
        Text(
            text = weather.weather,
            modifier = Modifier.padding(16.dp)
        )
        Text(
            text = weather.description,
            modifier = Modifier.padding(16.dp)
        )
        Text(
            text = weather.time,
            modifier = Modifier.padding(16.dp)
        )
        Text(
            text = weather.date,
            modifier = Modifier.padding(16.dp)
        )
        Text(
            text = weather.city,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SnackBarComponentPreview() {
    AppTheme {
        Surface {
            MainContent(
                modifier = Modifier.fillMaxSize(),
                allForecast = listOf(),
                onQueryChange = {}
            )
        }
    }
}