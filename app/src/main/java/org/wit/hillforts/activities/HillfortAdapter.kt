package org.wit.hillforts.activities

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.card_hillfort.view.*
import org.wit.hillfort.helpers.readImageFromPath
import org.wit.hillfort.models.HillfortModel
import org.wit.hillforts.R

interface HillfortListener {
    fun onHillfortClick(hillfort: HillfortModel)
}

class HillfortAdapter constructor(
    private var hillforts: List<HillfortModel>,
    private val listener: HillfortListener
) : RecyclerView.Adapter<HillfortAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        return MainHolder(
            LayoutInflater.from(parent?.context).inflate(
                R.layout.card_hillfort,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val hillfort = hillforts[holder.adapterPosition]
        holder.bind(hillfort, listener)
    }

    override fun getItemCount(): Int = hillforts.size

    class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(hillfort: HillfortModel, listener: HillfortListener) {
            itemView.hillfortTitle.text = hillfort.title
            //itemView.description.text = hillfort.description
            itemView.hillfortVisited.isChecked = hillfort.visited
            itemView.hillfortLoc.text = "Latitude:  "  +  hillfort.lat  +  ",\nLongitude:  " + hillfort.lng
            if(hillfort.images.size > 0)
                itemView.imageIcon.setImageBitmap(readImageFromPath(itemView.context, hillfort.images[0]))
            itemView.setOnClickListener { listener.onHillfortClick(hillfort) }
        }
    }
}