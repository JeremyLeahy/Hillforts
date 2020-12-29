package org.wit.hillforts.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_hillfort_list.*
import org.jetbrains.anko.*
import org.wit.hillfort.main.MainApp
import org.wit.hillfort.models.HillfortModel
import org.wit.hillfort.models.UserModel
import org.wit.hillforts.R

class HillfortListActivity : AppCompatActivity(), HillfortListener, AnkoLogger  {

    lateinit var app: MainApp
    private var user = UserModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hillfort_list)
        app = application as MainApp



        toolbar.title = title
        setSupportActionBar(toolbar)

        if (intent.hasExtra("user")) {
            user = intent.extras?.getParcelable<UserModel>("user")!!
            info("List" + user)
        }

        toolbar.title = title
        setSupportActionBar(toolbar)
        //supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        loadHillforts(user)
    }

    private fun loadHillforts(userModel: UserModel) {
        showHillforts(app.hillforts.findAllforUser(userModel))
    }

    fun showHillforts (hillforts: List<HillfortModel>) {
        recyclerView.adapter = HillfortAdapter(hillforts, this)
        recyclerView.adapter?.notifyDataSetChanged()
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        //val menuUser: MenuItem = menu?.findItem(R.id.user_name)!!
        //menuUser.setTitle(user?.email)
        user_name.setText(user?.firstName + " " + user?.lastName)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {
            R.id.item_add -> {
                startActivityForResult(intentFor<HillfortActivity>().putExtra("user", user),0)
            }


            R.id.item_settings -> {
                startActivityForResult(intentFor<SettingsActivity>().putExtra("user", user),0)
            }

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
        loadHillforts(user)
        super.onActivityResult(requestCode, resultCode, data)
    }



}

