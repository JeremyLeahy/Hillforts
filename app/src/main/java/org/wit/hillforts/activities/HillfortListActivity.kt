package org.wit.hillforts.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.wit.hillfort.main.MainApp
import org.wit.hillforts.R

class HillfortListActivity : AppCompatActivity() {

    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hillfort_list)
        app = application as MainApp
    }
}