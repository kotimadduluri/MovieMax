package com.common.ui.components.state

import com.common.R
import com.common.util.UiImage
import com.common.util.UiText


sealed class ActionState(
    val title: UiText,
    val message: UiText,
    val icon: UiImage
) {
    class LOADING(
        title: UiText = UiText.StringResource(R.string.loading),
        message: UiText = UiText.StringResource(R.string.loading_data),
        icon: UiImage = UiImage.DrawableResource(R.drawable.ic_wifi_off)
    ) : ActionState(title, message, icon)

    class ERROR(
        title: UiText = UiText.StringResource(R.string.retry),
        message: UiText = UiText.StringResource(R.string.error_something_went_wrong),
        icon: UiImage = UiImage.DrawableResource(R.drawable.ic_error)
    ) : ActionState(title, message, icon)

    class SUCCESS(
        title: UiText = UiText.StringResource(R.string.success),
        message: UiText = UiText.StringResource(R.string.completed),
        icon: UiImage = UiImage.DrawableResource(R.drawable.ic_success)
    ) : ActionState(title, message, icon)

    class WARNING(
        title: UiText = UiText.StringResource(R.string.warning),
        message: UiText = UiText.StringResource(R.string.warning_action_required),
        icon: UiImage = UiImage.DrawableResource(R.drawable.ic_warning)
    ) : ActionState(title, message, icon)
}