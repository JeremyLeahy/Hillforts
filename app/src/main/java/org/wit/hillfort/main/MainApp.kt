package org.wit.hillfort.main

import android.app.Application
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.wit.hillfort.models.HillfortMemStore
import org.wit.hillfort.models.HillfortModel
import org.wit.hillfort.models.HillfortStore

class MainApp : Application(), AnkoLogger {

    //val hillforts = ArrayList<HillfortModel>()
    lateinit var hillforts: HillfortStore

    override fun onCreate() {
        super.onCreate()
        hillforts = HillfortMemStore()
        info("Hillfort started")
    }
}