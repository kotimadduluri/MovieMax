package com.moviemax.view.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
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
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.common.ui.components.button.ButtonWithProgressBar
import com.common.ui.components.state.ActionState
import com.common.ui.components.state.ActionStateViewCard
import com.common.ui.components.text.TextView
import com.common.ui.theme.spacing
import com.common.util.UiText
import com.moviemax.R
import com.moviemax.model.auth.data.User
import com.moviemax.view.movie.MovieModule
import com.moviemax.view.UiState
import com.moviemax.view.auth.router.AuthRouter
import com.moviemax.viewmodel.auth.AuthViewModelIntent
import org.koin.androidx.compose.getKoin
import org.koin.androidx.compose.inject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    uiState: State<UiState>,
    navigate:(route:String)->Unit,
    navControl: NavHostController,
    newlyRegisteredUser:State<User?>,
    onEvent: (intent: AuthViewModelIntent) -> Unit
) {

    val router:AuthRouter = getKoin().get()

    val email = remember { mutableStateOf(TextFieldValue()) }
    val password = remember { mutableStateOf(TextFieldValue()) }

    if(uiState.value is UiState.Success<*>){  //need to improve
        router.onLoginCompleted(navControl)
    }

    Box(modifier = Modifier
        .padding(MaterialTheme.spacing.medium)
        .fillMaxSize()) {

        newlyRegisteredUser.value?.let {
            ActionStateViewCard(action = ActionState.SUCCESS(
                message = UiText.PlainString("Registration completed!")
            ))
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
                .padding(MaterialTheme.spacing.small),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextView(
                text = UiText.StringResource(R.string.login),
                textStyle = TextStyle(fontFamily = FontFamily.Cursive),
                fontSize = 40.sp
            )

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.large))

            TextField(
                label = {
                    Text(text = stringResource(id = R.string.email))
                },
                value = email.value,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = { email.value = it }
            )

            Spacer(modifier = Modifier.height(20.dp))

            TextField(
                label = {
                    Text(text = stringResource(id = R.string.password))
                },
                modifier = Modifier.fillMaxWidth(),
                value = password.value,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                onValueChange = { password.value = it })

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
            if(uiState.value is UiState.Error){
                TextView(
                    text = uiState.value.asError().message,
                    fontSize = 12.sp,
                    fontColor = Color.Red,
                    modifier = Modifier.fillMaxWidth()
                )
            }else {
                TextView(
                    text = UiText.PlainString("Credentials : test@email.com / Test@123"),
                    fontSize = 12.sp,
                    fontColor = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))

            ButtonWithProgressBar(
                text = UiText.StringResource(R.string.login),
                isLoading= (uiState.value is UiState.Loading),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(MaterialTheme.spacing.medium)
            ) {
                onEvent(
                    AuthViewModelIntent.Login(
                        email = email.value.text,
                        password = password.value.text,
                    )
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            ClickableText(
                text = AnnotatedString(UiText.StringResource(R.string.forgotPassword).asString()),
                onClick = {
                          //todo
                },
                style = TextStyle(
                    fontSize = 14.sp
                )
            )
        }

        ClickableText(
            text = AnnotatedString("Sign up here"),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(20.dp),
            onClick = {
               navControl.navigate(AuthModule.Registration.route)
            },
            style = TextStyle(
                fontSize = 14.sp,
                textDecoration = TextDecoration.Underline,
                color = MaterialTheme.colorScheme.primary
            )
        )
    }
}