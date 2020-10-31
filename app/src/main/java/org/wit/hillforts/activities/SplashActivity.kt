package org.wit.hillforts.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import org.jetbrains.anko.AnkoLogger
import org.wit.hillforts.R

class SplashActivity : AppCompatActivity(), AnkoLogger {

    //loading time of the splash screen
    private val SPLASH_TIME_OUT:Long = 3000 // 1 sec
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


        Handler().postDelayed({
            // This method will be executed once the timer is over
            // Start your app main activity

            startActivity(Intent(this, HillfortListActivity::class.java))

            // close this activity
            finish()
        }, SPLASH_TIME_OUT)
    }
}