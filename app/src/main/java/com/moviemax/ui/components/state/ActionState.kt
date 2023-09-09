package com.moviemax.ui.components.state

import com.moviemax.R

sealed class ActionState(
    val title: String,
    val message: String,
    val icon: Int
) {
    class LOADING(
        title: String = "Loading",
        message: String = "Loading data",
        icon: Int = R.drawable.ic_wifi_off
    ) : ActionState(title, message, icon)

    class ERROR(
        title: String = "Retry",
        message: String = "Something went wrong!",
        icon: Int = R.drawable.ic_error
    ) : ActionState(title, message, icon)

    class SUCCESS(
        title: String = "Completed",
        message: String = "Success!",
        icon: Int = R.drawable.ic_success
    ) : ActionState(title, message, icon)

    class WARNING(
        title: String,
        message: String = "Action required!",
        icon: Int = R.drawable.ic_warning
    ) : ActionState(title, message, icon)
}