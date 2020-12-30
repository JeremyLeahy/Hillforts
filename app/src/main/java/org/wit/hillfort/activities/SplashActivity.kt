package org.wit.hillfort.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import org.jetbrains.anko.AnkoLogger
import org.wit.hillforts.R

class SplashActivity : AppCompatActivity(), AnkoLogger {

    //loading time of the splash screen
    private val SPLASH_TIME_OUT:Long = 3000 // 1 sec
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            //once timer is over the Main activity will start
            startActivity(Intent(this, LoginActivity::class.java))
            // close this activity
            finish()
        }, SPLASH_TIME_OUT)
    }
}