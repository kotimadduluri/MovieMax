package com.moviemax.viewmodel.auth

import androidx.lifecycle.viewModelScope
import com.moviemax.common.BaseViewModel
import com.moviemax.model.Resource
import com.moviemax.model.auth.data.User
import com.moviemax.model.auth.usecase.LoginUseCase
import com.moviemax.model.auth.usecase.RegistrationUseCase
import com.moviemax.view.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

//Shared view model
class AuthViewModel(
    val loginUseCase: LoginUseCase,
    val registrationUseCase: RegistrationUseCase
) : BaseViewModel<UiState>(UiState.None) {

    private val _registrationStatus = MutableStateFlow<User?>(null)
    val registrationStatus: StateFlow<User?>
        get() = _registrationStatus

    fun onAction(intent: AuthViewModelIntent) {
        when (intent) {
            is AuthViewModelIntent.Login -> {
                login(intent.email, intent.password)
            }

            is AuthViewModelIntent.Register -> {
                register(intent.name, intent.email, intent.password)
            }

            is AuthViewModelIntent.Reset -> {
                uiState.value = UiState.None
            }
        }
    }

    private fun login(
        email: String,
        password: String
    ) {
        uiState.value = UiState.Loading

        viewModelScope.launch {
            val result = loginUseCase(
                email.trim(), password.trim()
            )

            uiState.value = when (result) {
                is Resource.Success<*> -> {
                    UiState.Success<Boolean>(true)
                }

                is Resource.Error -> {
                    UiState.Error(result.message)
                }
            }

        }
    }

    private fun register(
        name: String,
        email: String,
        password: String
    ) {

        uiState.value = UiState.Loading

        viewModelScope.launch {

            val user = User(
                name = name.trim(),
                email = email.trim(),
                password = password.trim()
            )

            val result = registrationUseCase(user)

            uiState.value = when (result) {
                is Resource.Success<*> -> {
                    _registrationStatus.emit(user)
                    UiState.Success<Boolean>(true)
                }

                is Resource.Error -> {
                    UiState.Error(result.message)
                }
            }

        }
    }


}

sealed class AuthViewModelIntent {

    data object Reset : AuthViewModelIntent()
    data class Login(
        val email: String,
        val password: String
    ) : AuthViewModelIntent()

    data class Register(
        val name: String,
        val email: String,
        val password: String
    ) : AuthViewModelIntent()

}