package com.moviemax.view.auth
sealed class AuthModule(val route:String){
    data object Login : AuthModule("login")
    data object Registration : AuthModule("registration")
    companion object{
        const val ModuleName="Authentication"
    }
}
