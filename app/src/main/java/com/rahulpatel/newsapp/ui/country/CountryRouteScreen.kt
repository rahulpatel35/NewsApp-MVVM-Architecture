package com.rahulpatel.newsapp.ui.country

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rahulpatel.newsapp.R
import com.rahulpatel.newsapp.data.model.Country
import com.rahulpatel.newsapp.ui.base.ShowError
import com.rahulpatel.newsapp.ui.base.ShowLoading
import com.rahulpatel.newsapp.ui.base.UiState
import com.rahulpatel.newsapp.ui.theme.orage

@Composable
fun CountryListRoute(
    onCountryClick: (countryId: String) -> Unit,
    countryListViewModel: CountryListViewModel = hiltViewModel()
) {
    val countryUiState by countryListViewModel.countryUiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier.padding(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TitleText()
        CountrySCreen(countryUiState, onCountryClick)
    }
}

@Composable
fun CountrySCreen(
    countryUiState: UiState<List<Country>>,
    onCountryClick: (String) -> Unit
) {
    when (countryUiState) {
        is UiState.Success -> {
            CountryList(countryUiState.data, onCountryClick)
        }

        is UiState.Error -> {
            ShowError(countryUiState.message)
        }

        is UiState.Loading -> {
            ShowLoading()
        }

    }
}

@Composable
fun CountryList(countrys: List<Country>, onNewsClick: (countryId: String) -> Unit) {
    LazyColumn {
        items(countrys.size) { index ->
            Country(country = countrys[index], onNewsClick)
        }
    }
}

@Composable
fun Country(country: Country, onNewsClick: (countryId: String) -> Unit) {
    Button(
        onClick = { onNewsClick(country.id) },
        colors = ButtonDefaults.buttonColors(
            containerColor = orage
        ),
        shape = RoundedCornerShape(5.dp),
        modifier = Modifier
            .padding(start = 20.dp, top = 5.dp, end = 20.dp, bottom = 5.dp)
            .fillMaxWidth(),
        contentPadding = PaddingValues(16.dp)
    ) {
        Text(
            text = country.name,
            color = Color.White,
            fontSize = 16.sp
        )
    }
}

@Composable
fun TitleText() {
    Text(
        text = stringResource(R.string.coutries),
        style = MaterialTheme.typography.labelLarge,
        fontSize = 20.sp,
        color = Color.Black,
        modifier = Modifier
            .padding(4.dp)
    )
}
