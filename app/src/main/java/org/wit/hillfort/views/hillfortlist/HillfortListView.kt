package org.wit.hillfort.views.hillfortlist

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_hillfort.*
import kotlinx.android.synthetic.main.activity_hillfort_list.*
import kotlinx.android.synthetic.main.activity_hillfort_list.user_name
import org.jetbrains.anko.*
import org.wit.hillfort.activities.HillfortAdapter
import org.wit.hillfort.activities.HillfortListener
import org.wit.hillfort.activities.LoginActivity
import org.wit.hillfort.models.HillfortModel
import org.wit.hillfort.models.UserModel
import org.wit.hillfort.views.BaseView
import org.wit.hillforts.R


class HillfortListView : BaseView(), HillfortListener {


    lateinit var presenter: HillfortListPresenter

    //private var user = UserModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hillfort_list)
        super.init(toolbar, false)
        //toolbar.title = title
        //setSupportActionBar(toolbar)

        //presenter = HillfortListPresenter(this)
        presenter = initPresenter(HillfortListPresenter(this)) as HillfortListPresenter


        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        presenter.loadHillforts()
    }

    override fun showHillforts(hillforts: List<HillfortModel>) {
        recyclerView.adapter = HillfortAdapter(hillforts, this)
        recyclerView.adapter?.notifyDataSetChanged()
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        user_name.setText(presenter.getUserName())
        return super.onCreateOptionsMenu(menu)
    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {

            R.id.item_add -> presenter.doAddHillfort()
            R.id.item_map -> presenter.doShowHillfortsMap()
            R.id.item_settings -> presenter.doShowSettings()
            R.id.item_favs -> presenter.doShowFavs()


            //R.id.item_favs -> presenter.doShowFavourites()

            /*
            R.id.item_add -> {
                startActivityForResult(intentFor<HillfortView>().putExtra("user", user),0)
            }

            R.id.item_map -> {
                startActivityForResult(intentFor<HillfortMapsActivity>().putExtra("user", user),0)

           */


            //R.id.item_map -> startActivity<HillfortMapsActivity>()


            /*
            R.id.item_settings -> {
                startActivityForResult(intentFor<SettingsActivity>().putExtra("user", user),0)
            }


            */
            /*
            R.id.item_favs -> {
                startActivityForResult(intentFor<FavouritesActivity>().putExtra("user", user),0)
            }
            */
            R.id.user_logout -> {
                startActivityForResult<LoginActivity>(0)
                toast(R.string.logout)
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
    //
    override fun onHillfortClick(hillfort: HillfortModel) {
        presenter.doEditHillfort(hillfort)

        //val intent = Intent(this, HillfortView::class.java)
        //intent.putExtra("user", user)
        //intent.putExtra("hillfort_edit", hillfort)
        //startActivityForResult(intent, 0)
        //startActivityForResult(intentFor<HillfortActivity>().putExtra("hillfort_edit", hillfort) ,0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        presenter.loadHillforts()
        recyclerView.adapter?.notifyDataSetChanged()
        super.onActivityResult(requestCode, resultCode, data)
    }
    override fun onResume() {
        super.onResume()
    }



}
