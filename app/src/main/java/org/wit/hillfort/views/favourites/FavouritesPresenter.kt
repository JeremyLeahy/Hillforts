package org.wit.hillfort.views.favourites

import android.content.Intent
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivity
import org.wit.hillfort.activities.SettingsActivity
import org.wit.hillfort.main.MainApp
import org.wit.hillfort.models.HillfortModel
import org.wit.hillfort.models.UserModel
import org.wit.hillfort.views.BasePresenter
import org.wit.hillfort.views.BaseView
import org.wit.hillfort.views.VIEW
import org.wit.hillfort.views.hillfort.HillfortView
import org.wit.hillfort.views.map.HillfortMapView
import org.wit.hillfort.views.settings.SettingsView


class FavouritesPresenter (view: BaseView) : BasePresenter(view){

    //var app: MainApp
    var user = UserModel()

    init {

        if (view.intent.hasExtra("user")) {
            user = view.intent.extras?.getParcelable("user")!!
        }
    }

    //fun getHillforts(userModel: UserModel) = app.hillforts.findFavsforUser(userModel)
    //fun getHillforts() = app.hillforts.findFavsforUser(user)


    fun doAddHillfort() {
        //view?.navigateTo(VIEW.HILLFORT, 0, "user", user)
        //view.startActivityForResult(view.intentFor<HillfortView>().putExtra("user", user), 0)
        //view.recyclerView.adapter?.notifyDataSetChanged()
    }

    fun doEditHillfort(hillfort: HillfortModel) {


        val intent = Intent(view, HillfortView::class.java)
        intent.putExtra("user", user)
        intent.putExtra("hillfort_edit", hillfort)
        view?.startActivityForResult(intent, 0)

    }

    fun getUserName(): String {
        return user.firstName + " " + user.lastName
    }

    fun loadHillforts() {
        view?.showHillforts(app.hillforts.findFavsforUser(user))///????????????????
        //view?.showHillforts(app.hillforts.findAll())
    }


    fun doShowHillfortsMap() {
        view?.startActivity<HillfortMapView>()
    }


    fun doShowSettings(){
        view?.startActivityForResult(view?.intentFor<SettingsView>()?.putExtra("user", user), 0)
        //view.startActivity<SettingsActivity>()
    }

    fun doShowFavs(){
        view?.startActivityForResult(view?.intentFor<FavouritesView>()?.putExtra("user", user), 0)
        //view.startActivity<FavouritesView>()
    }

}