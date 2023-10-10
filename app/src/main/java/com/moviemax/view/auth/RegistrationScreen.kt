package com.moviemax.view.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.sp
import com.common.ui.components.button.ButtonWithProgressBar
import com.common.ui.components.text.TextView
import com.common.ui.theme.spacing
import com.common.util.UiText
import com.moviemax.R
import com.moviemax.view.UiState
import com.moviemax.viewmodel.auth.AuthViewModelIntent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationScreen(
    uiState: State<UiState>,
    registrationCompleted: () -> Unit,
    onEvent: (intent: AuthViewModelIntent) -> Unit
) {
    val name = remember { mutableStateOf(TextFieldValue()) }
    val email = remember { mutableStateOf(TextFieldValue()) }
    val password = remember { mutableStateOf(TextFieldValue()) }

    if (uiState.value is UiState.Success<*>) {  //need to improve
        registrationCompleted()
    }

    Box(
        modifier = Modifier
            .padding(MaterialTheme.spacing.medium)
            .fillMaxSize()
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
                .padding(MaterialTheme.spacing.small),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextView(
                text = UiText.StringResource(R.string.signup_here),
                textStyle = TextStyle(fontFamily = FontFamily.Cursive),
                fontSize = 40.sp
            )

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.large))


            TextField(
                label = {
                    Text(text = stringResource(id = R.string.name))
                },
                value = name.value,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = { name.value = it }
            )

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))

            TextField(
                label = {
                    Text(text = stringResource(id = R.string.email))
                },
                value = email.value,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = { email.value = it }
            )

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))

            TextField(
                label = {
                    Text(text = stringResource(id = R.string.password))
                },
                modifier = Modifier.fillMaxWidth(),
                value = password.value,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                onValueChange = { password.value = it })

            if (uiState.value is UiState.Error) {
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
                TextView(
                    text = uiState.value.asError().message,
                    fontSize = 12.sp,
                    fontColor = Color.Red,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))

            ButtonWithProgressBar(
                text = UiText.StringResource(R.string.signup),
                isLoading = (uiState.value is UiState.Loading),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(MaterialTheme.spacing.medium)
            ) {
                onEvent(
                    AuthViewModelIntent.Register(
                        name = name.value.text,
                        email = email.value.text,
                        password = password.value.text,
                    )
                )
            }
        }
    }
}