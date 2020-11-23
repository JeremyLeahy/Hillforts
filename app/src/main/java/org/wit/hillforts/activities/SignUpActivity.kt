package org.wit.hillforts.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
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

            //resetting
            signup_firstName.setBackgroundResource(android.R.drawable.edit_text)
            signup_lastName.setBackgroundResource(android.R.drawable.edit_text)
            signup_email.setBackgroundResource(android.R.drawable.edit_text)
            signup_password.setBackgroundResource(android.R.drawable.edit_text)

            //taken from activity_signup.xml
            user.firstName = signup_firstName.text.toString()
            user.lastName = signup_lastName.text.toString()
            user.email = signup_email.text.toString()
            user.password = signup_password.text.toString()


            var userMessage = ""

            if (user.firstName.isEmpty() || user.lastName.isEmpty())
            //toast(getString(R.string.fill_all_fields))
                userMessage += getString(R.string.fill_all_fields)

            if (user.email.isEmpty()) {
                //toast(getString(R.string.enter_Email))
                signup_email.setHint(R.string.enter_Email)
                userMessage += getString(R.string.enter_Email) + '\n'
                signup_email.setBackgroundResource(R.drawable.red_border)
            }
            if (user.password.isEmpty()) {
                //toast(getString(R.string.enter_Password))
                userMessage += getString(R.string.enter_Password)
                signup_password.setBackgroundResource(R.drawable.red_border)
            }
            if (user.password.length < 8) {
                //toast(getString(R.string.password_short))
                userMessage += getString(R.string.password_short) + '\n'
                signup_password.setBackgroundResource(R.drawable.red_border)
            }
            if (signup_password.text.toString() != signup_password2.text.toString()) {
                //toast(getString(R.string.password_mismatch))
                userMessage += getString(R.string.password_mismatch) + '\n'
                signup_password.setBackgroundResource(R.drawable.red_border)
                signup_password2.setBackgroundResource(R.drawable.red_border)
            }

            if (userMessage.length > 0)

                toast(userMessage)
            else {
                app.users.create(user.copy())
                info("new user created: ${user}")



                setResult(AppCompatActivity.RESULT_OK)
                finish()
                //startActivity(intentFor<HillfortListActivity>().putExtra("user", user))
                //startActivityForResult(intentFor<HillfortListActivity>().putExtra("User", user), 0)
            }


        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_cancel, menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {
            R.id.item_cancel -> {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                toast("Signup cancelled")
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

}