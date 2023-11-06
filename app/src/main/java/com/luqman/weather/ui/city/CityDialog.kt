package com.luqman.weather.ui.city

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.luqman.weather.core.network.model.Resource
import com.luqman.weather.data.repository.model.City
import com.luqman.weather.uikit.component.LoadingComponent
import com.luqman.weather.uikit.component.MessageScreenComponent
import com.luqman.weather.core.R as CoreR

@Composable
fun CityDialog(onCitySelected: (String) -> Unit, onDismiss: () -> Unit) {
    val viewModel: CityDialogViewModel = viewModel()
    val state = viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getCity()
    }

    Dialog(
        onDismissRequest = onDismiss,
        content = {
            when (state.value.getDataState) {
                is Resource.Success -> CityDialogContent(
                    state.value.getDataState.data.orEmpty(),
                    onCitySelected
                )

                is Resource.Error -> {
                    MessageScreenComponent(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp),
                        title = stringResource(id = CoreR.string.error_title),
                        message = stringResource(
                            id = CoreR.string.unknown_error_exception
                        ),
                        showActionButton = false
                    )
                }

                is Resource.Loading -> LoadingComponent(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                )
            }

        }
    )
}

@Composable
fun CityDialogContent(cities: List<City>, onCitySelected: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
    ) {
        LazyColumn {
            if (cities.isEmpty()) {
                item {
                    MessageScreenComponent(title = stringResource(id = CoreR.string.error_title))
                }
            } else {
                items(cities) { city ->
                    CityListItem(city = city.name, onCitySelected = onCitySelected)
                }
            }
        }
    }

}

@Composable
fun CityListItem(city: String, onCitySelected: (String) -> Unit) {
    ListItem(
        headlineContent = { Text(city) },
        leadingContent = { Icon(Icons.Default.LocationOn, contentDescription = null) },
        modifier = Modifier.clickable { onCitySelected(city) }
    )
}