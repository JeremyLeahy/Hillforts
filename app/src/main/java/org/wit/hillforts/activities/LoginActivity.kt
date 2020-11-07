package org.wit.hillforts.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast
import org.wit.hillfort.main.MainApp
import org.wit.hillfort.models.UserModel
import org.wit.hillforts.R

class LoginActivity: AppCompatActivity(){

    lateinit var app: MainApp
    var user = UserModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        app = application as MainApp
        toolbarAdd.title = title
        setSupportActionBar(toolbarAdd)


        btnLogin.setOnClickListener() {
            user.email = email.text.toString()
            user.password = password.text.toString()
            var currentUser = app.users.authenticateUser(user)
            if (currentUser == false) toast(getString(R.string.email_not_found))
            else {
                startActivity( intentFor<HillfortListActivity>().putExtra("user", user))


                //startActivityForResult( intentFor<HillfortListActivity>().putExtra("user", user), 0)
                //user = currentUser
                //if (password.text.toString() == user.password)  startActivityForResult( intentFor<HillfortListActivity>().putExtra("User", user), 0)
                //else toast(getString(R.string.incorrect_password))
            }
        }

        //when button is pressed-goes to sign up page
        btnSignup.setOnClickListener() {
            startActivityForResult(intentFor<SignUpActivity>(), 0)
        }
    }


}