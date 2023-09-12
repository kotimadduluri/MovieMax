package com.moviemax.fake

import com.common.util.UiText
import com.moviemax.R
import com.moviemax.common.meetingProfileBasicValidation
import com.moviemax.model.Resource
import com.moviemax.model.auth.data.User
import com.moviemax.model.auth.repository.AuthRepository

//todo temp cache should be replaces with real system
class FakeAuthRepositoryImp : AuthRepository {

    //for fake user details purpose
    private val _fakeUsersSource = mutableListOf<User>().apply {
        add(
            User(
                name = "Test user",
                email = "test@email.com",
                password = "Test@123"
            )
        )
    }

    private val fakeUsersSource: List<User>
        get() = _fakeUsersSource

    override suspend fun login(email: String, password: String): Resource {
        val userDetails = lookForUserWithCredentials(email, password)
        val userRecordFound = userDetails?.let { true } ?: false
        return when {
            userRecordFound -> Resource.Success<User>(userDetails)
            else -> Resource.Error(message = UiText.StringResource(R.string.user_records_not_available))
        }
    }

    override suspend fun registration(
        user: User
    ): Resource {

        val metBasicRequirements = meetingProfileBasicValidation(
            user.name,
            user.email,
            user.password
        )
        val userData = lookForUserByEmail(user.email)
        val userNotExist = lookForUserByEmail(user.email)?.let { false } ?: true
        return when {
            userNotExist && metBasicRequirements -> {
                _fakeUsersSource.add(user) //to cache user data
                Resource.Success<Boolean>(true)
            }

            !metBasicRequirements -> Resource.Error(message = UiText.StringResource(R.string.registration_validation_error))

            userNotExist -> {
                Resource.Error(message = UiText.StringResource(R.string.user_details_exist))
            }

            else -> {
                Resource.Error(message = UiText.StringResource(R.string.fail_to_register_user))
            }
        }
    }

    private fun lookForUserByEmail(email: String): User? = fakeUsersSource.find { user ->
        email == user.email
    }

    private fun lookForUserWithCredentials(
        email: String,
        password: String
    ): User? = fakeUsersSource.findLast { user ->
        email == user.email && password == user.password
    }
}

