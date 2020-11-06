package org.wit.hillfort.models

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.jetbrains.anko.AnkoLogger
import org.wit.hillfort.helpers.*
import java.util.*

val USER_JSON_FILE = "users.json"
val userGsonBuilder = GsonBuilder().setPrettyPrinting().create()
val userlistType = object : TypeToken<java.util.ArrayList<UserModel>>() {}.type


fun generateRandomUserId(): Long {
    return Random().nextLong()
}

class UserJSONStore : UserStore, AnkoLogger {

    val context: Context
    var users = mutableListOf<UserModel>()

    constructor (context: Context) {
        this.context = context
        if (exists(context, USER_JSON_FILE)) {
            deserialize()
        }
    }

    // Function returns a list of all users in the Users Array
    override fun findAll(): MutableList<UserModel> {
        return users
    }

    override fun findUser(user: UserModel) : Boolean {
        var foundUser: UserModel? = users.find{ p -> p.id == user.id }
        if(foundUser != null) {
            return true
        }
        return false
    }
    //authenticates user
    override fun authenticateUser(user: UserModel) : Boolean {
        var foundUser: UserModel? = users.find { p -> p.email == user.email && p.password == user.password}
        if (foundUser != null) {
            return true
        }
        return false
    }



    override fun create(user: UserModel) {
        user.id = generateRandomUserId()
        users.add(user)

        serialize()
    }

    override fun delete(user: UserModel) {
        users.remove(user)
        serialize()
    }



    private fun serialize() {
        val jsonString = userGsonBuilder.toJson(users, listType)
        write(context, USER_JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, USER_JSON_FILE)
        users = Gson().fromJson(jsonString, userlistType)
    }
}