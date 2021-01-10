package org.wit.hillfort.views.hillfort

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.gms.maps.GoogleMap
import kotlinx.android.synthetic.main.activity_hillfort.*
import kotlinx.android.synthetic.main.activity_hillfort.user_name
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.toast
import org.wit.hillfort.helpers.readImageFromPath
import org.wit.hillfort.models.HillfortModel
import org.wit.hillfort.models.UserModel
import org.wit.hillfort.views.BaseView
import org.wit.hillforts.R

class HillfortView : BaseView(), AnkoLogger {

    //presenter object
    lateinit var presenter: HillfortPresenter
    lateinit var map: GoogleMap
    var hillfort = HillfortModel()
    //var user = UserModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hillfort)
        super.init(toolbarAdd, true)


        //init(toolbarAdd)



        /*
        toolbarAdd.title = title
        setSupportActionBar(toolbarAdd)
        //supportActionBar?.setDisplayHomeAsUpEnabled(true)
        info("Hillfort Activity started..")
    */
        presenter = initPresenter (HillfortPresenter(this)) as HillfortPresenter
        //creating presenter object

        //presenter = HillfortPresenter(this)

        mapView.onCreate(savedInstanceState);
    mapView.getMapAsync {
      map = it
      presenter.doConfigureMap(map)
        it.setOnMapClickListener { presenter.doSetLocation() }//
    }



        chooseImage.setOnClickListener {
            presenter.cachePlacemark( hillfortTitle.text.toString(), description.text.toString(), ratingBar.rating,
                visited.isChecked, favourite.isChecked, additionalHillfortNotes.text.toString())
            presenter.doSelectImage() }
/*
        hillfortLocation.setOnClickListener {
            presenter.cachePlacemark(hillfortTitle.text.toString(), description.text.toString(), ratingBar.rating,
                visited.isChecked, favourite.isChecked, additionalHillfortNotes.text.toString())
            presenter.doSetLocation() }
*/
    }

    //this method will be called bt the presenter when it has a hillfort to display
    override fun showHillfort(hillfort: HillfortModel) {
        hillfortTitle.setText(hillfort.title)
        description.setText(hillfort.description)
        ratingBar.setRating(hillfort.rating)
        visited.isChecked = hillfort.visited
        favourite.isChecked = hillfort.favourite
        lat.setText(hillfort.lat.toString())
        longitude.setText(hillfort.lng.toString())
        additionalHillfortNotes.setText(hillfort.additionalNotes)

        if(hillfort.images.size > 0 )
            hillfortImage.setImageBitmap(readImageFromPath(this, hillfort.images[0]))
        if(hillfort.images.size > 1)
            hillfortImage2.setImageBitmap(readImageFromPath(this, hillfort.images[1]))
        if(hillfort.images.size > 2)
            hillfortImage3.setImageBitmap(readImageFromPath(this, hillfort.images[2]))
        if(hillfort.images.size > 3)
            hillfortImage4.setImageBitmap(readImageFromPath(this, hillfort.images[3]))
        if (hillfort.images.size <=3) {
            chooseImage.setText(R.string.add_hillfort_image)
        }

        lat.setText("%.6f".format(hillfort.lat))
        longitude.setText("%.6f".format(hillfort.lng))
    }




    @SuppressLint("SetTextI18n")
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_hillfort, menu)
        if (presenter.edit) menu.getItem(0).setVisible(true)
        user_name.setText(presenter.getUserName())
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {
            R.id.item_save -> {
                if (hillfortTitle.text.toString().isEmpty()) {
                    toast(R.string.enter_hillfort_title)
                } else {
                    presenter.doAddOrSave(
                        hillfortTitle.text.toString(), description.text.toString(), ratingBar.rating,
                        visited.isChecked, favourite.isChecked, additionalHillfortNotes.text.toString())
                }
            }
            R.id.item_cancel -> {
                presenter.doCancel()
            }
            R.id.item_delete -> {
                presenter.doDelete()
            }
            R.id.user_logout -> {
                presenter.doLogout()
            }
            /*
            R.id.user_logout -> {
              //presenter.doLogout()
              startActivityForResult(intentFor<LoginActivity>(),0)
              toast(R.string.logout)
            }

             */
        }
        return super.onOptionsItemSelected(item)
    }
    //
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //if user has chosen something
        if (data != null) {
            presenter.doActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onBackPressed() {

        info("in Back Presses")

    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

}
