package org.wit.hillfort.models

import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

class HillfortMemStore : HillfortStore, AnkoLogger {

    val hillforts = ArrayList<HillfortModel>()

    override fun findAll(): List<HillfortModel> {
        return hillforts
    }

    override fun findById(id:Long) : HillfortModel? {
        val foundHillfort: HillfortModel? = hillforts.find { it.id == id }
        return foundHillfort
    }

    override fun findAllforUser(userModel: UserModel): MutableList<HillfortModel> {
        var hillfortsforLoggedInUser = mutableListOf<HillfortModel>()
        for (hill in hillforts) {
            if (hill.user == userModel)
                hillfortsforLoggedInUser.add(hill)
        }
        return hillfortsforLoggedInUser
    }

    override fun create(hillfort: HillfortModel) {
        hillfort.id = getId()
        hillforts.add(hillfort)
        logAll()
    }


    // When a hillfort is updated
    override fun update(hillfort: HillfortModel) {
        //looking for the match
        var foundHillfort: HillfortModel? = hillforts.find { p -> p.id == hillfort.id }
        //if you find not null ie. a match
        if (foundHillfort != null) {
            foundHillfort.title = hillfort.title
            foundHillfort.description = hillfort.description
            foundHillfort.image = hillfort.image
            foundHillfort.lat = hillfort.lat
            foundHillfort.lng = hillfort.lng
            foundHillfort.zoom = hillfort.zoom
            logAll()
        }
    }

    override fun delete(hillfort: HillfortModel) {
        hillforts.remove(hillfort)
    }

    fun logAll() {
        hillforts.forEach{ info("${it}") }
    }


}