package org.wit.hillforts.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_hillfort.*
import kotlinx.android.synthetic.main.activity_hillfort.toolbarAdd
import kotlinx.android.synthetic.main.activity_signup.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.toast
import org.wit.hillfort.main.MainApp
import org.wit.hillfort.models.UserModel
import org.wit.hillforts.R

class SignUpActivity: AppCompatActivity(), AnkoLogger {

    lateinit var app: MainApp
    var user = UserModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        toolbarAdd.title = title
        setSupportActionBar(toolbarAdd)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        app = application as MainApp


        btnNewSignup.setOnClickListener() {


            //taken from activity_signup.xml
            user.email = signup_email.text.toString()
            user.password = signup_password.text.toString()


            if (user.email.isEmpty()) {
                //called from strings.xml
                toast(R.string.enter_newEmail)

            } else if (user.password.isEmpty()) {
                toast(R.string.enter_newPassword)


            } else {
                app.users.create(user.copy())
                info("new User created: ${user}")
            }

            info("Sign Up Button Pressed: ${user}")

            setResult(AppCompatActivity.RESULT_OK)
            finish()
        }

    }
}