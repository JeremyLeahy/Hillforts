package org.wit.hillforts.activities


import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
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
import androidx.appcompat.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.Toast

class SettingsActivity: AppCompatActivity(), AnkoLogger {

    lateinit var app: MainApp

    var user = UserModel()
    var loggedInUser = UserModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        toolbarAdd.title = title
        setSupportActionBar(toolbarAdd)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        app = application as MainApp


        info ("Update Activity Started")

        if (intent.hasExtra("user"))
        {
            loggedInUser = intent.extras?.getParcelable<UserModel>("user")!!
            //val currentUser = intent.extras?.getParcelable<UserModel>("User_edit")!!
            //user = app.users.findUserByEmail(currentUser.email)!!
            info("User: ")
            info(loggedInUser)
        }

        buttonUpdateUser.setOnClickListener() {



            //loggedInUser.firstName = signup_firstName.text.toString()
            //loggedInUser.lastName = signup_lastName.text.toString()
            loggedInUser.email = update_email.text.toString()
            loggedInUser.password = update_password.text.toString()


            if (loggedInUser.email.isEmpty()) {
                //called from strings.xml
                toast(R.string.enter_newEmail)

            } else if (loggedInUser.password.isEmpty()) {
                toast(R.string.enter_newPassword)



            } else {
                app.users.updateUser(loggedInUser.copy())
                toast(R.string.userDetails_updated)
                info("User details updated: ${user}")
            }

            info("Sign Up Button Pressed: ${user}")
            setResult(AppCompatActivity.RESULT_OK)
            finish()
        }

    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_settings, menu)
        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {
            R.id.item_cancel -> {
                finish()
            }

            R.id.user_delete -> {

/*
                val builder = AlertDialog.Builder(this@SettingsActivity)
                builder.setMessage("Are you sure you want to Delete?")
                    .setCancelable(false)
                    .setPositiveButton(
                        "Yes")
                         { dialog, id -> app.users.delete(loggedInUser) }
                    .setNegativeButton("No", DialogInterface.OnClickListener { dialog, id -> dialog.cancel() })
                val alert = builder.create()
                alert.show()
                */

                toast(R.string.deleted_user)
                startActivityForResult(intentFor<LoginActivity>(),0)
                //finish()
            }

            R.id.user_logout -> {
                startActivityForResult(intentFor<LoginActivity>(),0)
                toast(R.string.logout)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}