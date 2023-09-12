package com.common.validator

import com.common.R
import com.common.util.UiText

fun interface FieldValidator{
    fun validate(value:String):UiText?
}

val emailValidator = FieldValidator{email->
    val emailRegex = Regex("""^[A-Za-z0-9+_.-]+@(.+)$""")
    val isValidEmail=emailRegex.matches(email)
    if(isValidEmail){
        return@FieldValidator null
    }
    UiText.StringResource(R.string.invalid_emaild)
}

val nameValidator = FieldValidator{name->

    val isValidName=name.isNotBlank() && name.all { it.isLetter() }

    if(isValidName){
        return@FieldValidator null
    }
    UiText.StringResource(R.string.invalid_name)
}

val passwordValidator = FieldValidator{password->

    // Check if the password meets the following criteria:
    // 1. Minimum length of 8 characters.
    // 2. Contains at least one uppercase letter.
    // 3. Contains at least one lowercase letter.
    // 4. Contains at least one digit (0-9).
    // 5. Contains at least one special character (e.g., !, @, #, $, etc.).

    val minLength = 8
    val hasUppercase = password.any { it.isUpperCase() }
    val hasLowercase = password.any { it.isLowerCase() }
    val hasDigit = password.any { it.isDigit() }
    val hasSpecialChar = password.any { it.isLetterOrDigit().not() }

    val isValidPassword = password.length >= minLength &&
            hasUppercase &&
            hasLowercase &&
            hasDigit &&
            hasSpecialChar

    if(password.length >= minLength){
        return@FieldValidator null
    }
    UiText.StringResource(R.string.invalid_password)
}