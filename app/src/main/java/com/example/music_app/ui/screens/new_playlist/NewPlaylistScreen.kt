package com.example.music_app.ui.screens.new_playlist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.music_app.R

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun NewPlaylistScreen(
    viewModel: NewPlaylistViewModel = viewModel(factory = NewPlaylistViewModel.Factory)
) {

    val nameFieldState = remember { mutableStateOf("") }
    val descriptionFieldState = remember { mutableStateOf("") }

    val isNameValid = remember { mutableStateOf(false) }
    val isNameFieldActive = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = CenterHorizontally
    ) {
        Surface(
            color = MaterialTheme.colorScheme.primaryContainer,
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
        ) {
        }
        Column(
            horizontalAlignment = CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.primaryContainer)
                .padding(20.dp)
        ) {
            Text(text = "Name")
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(

                value = nameFieldState.value,
                onValueChange = {
                    nameFieldState.value = it
                    isNameValid.value = it.isNotBlank()
                    isNameFieldActive.value = true
                },
                isError = !isNameValid.value && isNameFieldActive.value,
                placeholder = {
                    if (!isNameFieldActive.value) {
                        Text(stringResource(R.string.name_field))
                    } else {
                        Text(stringResource(R.string.empty_field_error), color = Color.Red)
                    }
                },
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color.White)

            )
            Spacer(modifier = Modifier.height(30.dp))
            Text(text = "Description")
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                value = descriptionFieldState.value,
                onValueChange = { descriptionFieldState.value = it },
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color.White),
                placeholder = {
                    Text(stringResource(R.string.description_field))
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Green,
                    unfocusedBorderColor = Color.Red,
                    disabledBorderColor = Color.Gray
                )

            )
        }
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.primaryContainer)
        ) {
            Button(
                onClick = {
                    if (isNameValid.value) {
                        viewModel.createPlaylist(
                            name = nameFieldState.value,
                            description = descriptionFieldState.value
                        )
                    } else {
                        isNameFieldActive.value = true
                    }
                },
                modifier = Modifier
                    .width(170.dp)
                    .height(40.dp)
            ) {
                Text(text = stringResource(R.string.create_button))
            }
        }
    }
}
