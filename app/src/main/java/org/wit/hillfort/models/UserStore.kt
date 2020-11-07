package org.wit.hillfort.models

interface UserStore {

    fun findAllUsers(): List<UserModel>
    fun create(userModel: UserModel)
    fun authenticateUser(userModel: UserModel): Boolean
    fun findUser(userModel: UserModel): Boolean
    fun delete(userModel: UserModel)
    fun updateUser(userModel: UserModel)
}