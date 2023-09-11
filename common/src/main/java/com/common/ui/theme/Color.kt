package com.common.ui.theme

import androidx.compose.ui.graphics.Color

object DarkColours{
    val Primary = Color(0xFF009688)
    val Secondary = Color(0xFF673AB7)
    val Tertiary = Color(0xFF8BC34A)
    val Surface = Color(0xFF0E101F)
    val onSurface = Color(0xFFFFFFFF)

    //additional colors
    val movieCardStatusColor = Primary
    val movieCardNetworkColor = Color(0xFFFFC107)
}

object LightColours{
    val Primary = Color(0xFF009688)
    val Secondary = Color(0xFF673AB7)
    val Tertiary = Color(0xFF8BC34A)
    val Surface = Color(0xFF0E101F)
    val onSurface = Color(0xFF333333)

    //additional colors
    val movieCardStatusColor = Primary
    val movieCardNetworkColor = Secondary
}

object GetColors{

    fun movieCardStatusColor(isDark:Boolean) = if(isDark) DarkColours.movieCardStatusColor else LightColours.movieCardStatusColor
    fun movieCardNetworkColor(isDark:Boolean) = if(isDark) DarkColours.movieCardNetworkColor else LightColours.movieCardNetworkColor
    fun getFlavourColor(isDark:Boolean) = if(isDark) DarkColours.Secondary else LightColours.Primary

}