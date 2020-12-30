package org.wit.hillfort.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_hillfort.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast
import org.wit.hillfort.helpers.readImage
import org.wit.hillfort.helpers.readImageFromPath
import org.wit.hillfort.helpers.showImagePicker
import org.wit.hillfort.main.MainApp
import org.wit.hillfort.models.HillfortModel
import org.wit.hillfort.models.Location
import org.wit.hillfort.models.UserModel
import org.wit.hillforts.R

class HillfortActivity : AppCompatActivity(), AnkoLogger {

  var hillfort = HillfortModel()
  lateinit var app: MainApp
  val IMAGE_REQUEST = 1
  val LOCATION_REQUEST = 2
  var image_number = 0
  var user = UserModel()

  //var location = Location(52.245696, -7.139102, 15f)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_hillfort)
    toolbarAdd.title = title
    setSupportActionBar(toolbarAdd)
    //supportActionBar?.setDisplayHomeAsUpEnabled(true)
    info("Hillfort Activity started..")

    app = application as MainApp


    var edit = false
    //this code is called when we click on the hillfort list card-listbox
    if (intent.hasExtra("hillfort_edit")) {
      edit = true
      hillfort = intent.extras?.getParcelable<HillfortModel>("hillfort_edit")!!
      hillfortTitle.setText(hillfort.title)
      description.setText(hillfort.description)
      visited.isChecked = hillfort.visited
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
      btnAdd.setText(R.string.edit_hillfort)
    }

    if (intent.hasExtra("user")) {
      user = intent.extras?.getParcelable("user")!!
    }

    //if you click on the image, it checks the hillfort images size
    //hillfortImage.setOnClickListener(){
  //checking to see what the image number was that I have stored
      //if (image_number >= hillfort.images.size ){
        //set it back to zero
        //image_number=0;
     // }

      //hillfortImage.setImageBitmap(readImageFromPath(this, hillfort.images[image_number]))
      //image_number++;

    //}

    btnAdd.setOnClickListener() {
      hillfort.user = user
      hillfort.title = hillfortTitle.text.toString()
      hillfort.description = description.text.toString()
      hillfort.visited = visited.isChecked
      hillfort.additionalNotes = additionalHillfortNotes.text.toString()
      if (hillfort.title.isEmpty()) {
        //called from strings.xml
        toast(R.string.enter_hillfort_title)
      } else {
        if (edit) {
          app.hillforts.update(hillfort.copy())
        } else {
          app.hillforts.create(hillfort.copy())
          toast(R.string.hillfort_added)
        }
      }
        info("add Button Pressed: ${hillfort}")

        setResult(AppCompatActivity.RESULT_OK)
        finish()
      }

    chooseImage.setOnClickListener {
      //passing it in this activity, and the value 1-image request
      showImagePicker(this, IMAGE_REQUEST)
    }

    hillfortLocation.setOnClickListener {
      val location = Location(52.245696, -7.139102, 15f)
      if (hillfort.zoom != 0f) {
        location.lat =  hillfort.lat
        location.lng = hillfort.lng
        location.zoom = hillfort.zoom
      }
      startActivityForResult(intentFor<MapActivity>().putExtra("location", location), LOCATION_REQUEST)
    }


  }


  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    menuInflater.inflate(R.menu.menu_hillfort, menu)
    user_name.setText(user?.firstName + " " + user?.lastName)
    return super.onCreateOptionsMenu(menu)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item?.itemId) {
      R.id.item_cancel -> {
        finish()
      }

      R.id.item_delete -> {
        app.hillforts.delete(hillfort)
        toast(R.string.deleted_hillfort)
        finish()
      }
/*
      R.id.item_delete -> {

        val builder = AlertDialog.Builder(this)
        builder.setMessage("Are you sure you want to Delete this Hillfort?")
          .setCancelable(false)
          .setPositiveButton(
            "Yes")
          { dialog, id -> app.hillforts.delete(hillfort)
            toast(R.string.deleted_hillfort)
            //startActivityForResult(intentFor<HillfortListActivity>().putExtra("user", user))
            //startActivity( intentFor<HillfortListActivity>().putExtra("user", realUser))
            startActivityForResult(intentFor<HillfortListActivity>().putExtra("user", user),0)}
          .setNegativeButton("No", DialogInterface.OnClickListener { dialog, id -> dialog.cancel() })
        val alert = builder.create()
        alert.show()
      }
*/

      R.id.user_logout -> {
        startActivityForResult(intentFor<LoginActivity>(),0)
        toast(R.string.logout)
      }
    }
    return super.onOptionsItemSelected(item)
  }
//
  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    when (requestCode) {
      IMAGE_REQUEST -> {
        //if user has chosen something
        if (data != null) {
          hillfort.image = data.getData().toString()
          if(hillfort.images.size < 4)
            hillfort.images.add(data.getData().toString())
          else toast("No more images can be added")
          hillfortImage.setImageBitmap(readImage(this, resultCode, data))
          chooseImage.setText(R.string.add_hillfort_image)
        }
      }
      LOCATION_REQUEST -> {
        if (data != null) {
          val location = data.extras?.getParcelable<Location>("location")!!
          hillfort.lat = location.lat
          hillfort.lng = location.lng
          hillfort.zoom = location.zoom
        }
      }
    }
  }


}