package com.moviemax.common

import com.common.util.UiText
import com.common.validator.emailValidator
import com.common.validator.nameValidator
import com.common.validator.passwordValidator
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

//todo improve performance
suspend fun performLoginValidation(email: String, password: String): List<UiText?> =
    withContext(DefaultDispatcherProvider().io) {
        val emailError=async { emailValidator.validate(email) }.await()
        val passwordError=async { passwordValidator.validate(password) }.await()
        return@withContext listOfNotNull(
            emailError,passwordError
        )
    }

//todo improve performance
suspend fun performRegistrationValidation(
    email: String,
    name: String,
    password: String
): List<UiText> = withContext(DefaultDispatcherProvider().io) {

    val nameError=async { nameValidator.validate(name) }.await()
    val emailError=async { emailValidator.validate(email) }.await()
    val passwordError=async { passwordValidator.validate(password) }.await()
    return@withContext listOfNotNull(
        nameError,emailError,passwordError
    )
}

fun meetingProfileBasicValidation(
    name:String,
    email:String,
    password: String
) : Boolean{
    return name.trim().length>=4 && email.trim().length>=4 && password.trim().length>=4
}