package com.example.cookingapp.user

import androidx.lifecycle.LiveData

class UserRepository(private val userDao: UserDao) {

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    val userState: LiveData<User> = userDao.getUser("current")

    suspend fun update(user: User) {
        userDao.updateUser(user)
    }
}