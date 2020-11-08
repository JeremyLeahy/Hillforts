package org.wit.hillforts.activities

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_hillfort_list.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivityForResult
import org.jetbrains.anko.toast
import org.wit.hillfort.main.MainApp
import org.wit.hillfort.models.HillfortModel
import org.wit.hillfort.models.UserModel
import org.wit.hillforts.R

class HillfortListActivity : AppCompatActivity(), HillfortListener  {

    lateinit var app: MainApp
    private var user = UserModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hillfort_list)
        app = application as MainApp

        toolbar.title = title
        setSupportActionBar(toolbar)

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        loadHillforts()
    }

    private fun loadHillforts() {
        showHillforts(app.hillforts.findAll())
    }

    fun showHillforts (hillforts: List<HillfortModel>) {
        recyclerView.adapter = HillfortAdapter(hillforts, this)
        recyclerView.adapter?.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {
            R.id.item_add -> {
                startActivityForResult<HillfortActivity>(0)
            }

            /*
            R.id.item_settings -> {
                startActivityForResult<SettingsActivity>(0)
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
        val intent = Intent(this, HillfortActivity::class.java)
        intent.putExtra("user", user)
        intent.putExtra("hillfort_edit", hillfort)
        startActivityForResult(intent, 0)
        //startActivityForResult(intentFor<HillfortActivity>().putExtra("hillfort_edit", hillfort) ,0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        loadHillforts()
        super.onActivityResult(requestCode, resultCode, data)
    }



}

