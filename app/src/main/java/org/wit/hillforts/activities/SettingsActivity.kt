package org.wit.hillforts.activities


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_hillfort.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.toolbarAdd
import kotlinx.android.synthetic.main.activity_settings.*
import kotlinx.android.synthetic.main.activity_signup.*
import kotlinx.android.synthetic.main.activity_signup.btnNewSignup
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast
import org.wit.hillfort.main.MainApp
import org.wit.hillfort.models.UserModel
import org.wit.hillforts.R

class SettingsActivity: AppCompatActivity(), AnkoLogger {

    lateinit var app: MainApp
    var updateUser = UserModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        toolbarAdd.title = title
        setSupportActionBar(toolbarAdd)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        app = application as MainApp


        buttonUpdateUser.setOnClickListener() {


            //taken from activity_signup.xml
            updateUser.firstName = signup_firstName.text.toString()
            updateUser.lastName = signup_lastName.text.toString()
            updateUser.email = signup_email.text.toString()
            updateUser.password = signup_password.text.toString()


            if (updateUser.email.isEmpty()) {
                //called from strings.xml
                toast(R.string.enter_newEmail)

            } else if (updateUser.password.isEmpty()) {
                toast(R.string.enter_newPassword)



            } else {
                app.users.create(updateUser.copy())
                info("new User created: ${updateUser}")
            }

            info("Sign Up Button Pressed: ${updateUser}")

            setResult(AppCompatActivity.RESULT_OK)
            finish()
        }

    }
}