package com.example.app.winsaved

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.app.R

class SavedAdapter(var savedPlaces: List<SavedPlaces>, var context: Context) : RecyclerView.Adapter<SavedAdapter.SavedViewHolder>(){

    class SavedViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.saved_image_in_list)
        val title: TextView = view.findViewById(R.id.saved_title_in_list)
        val desc: TextView = view.findViewById(R.id.saved_desc_in_list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_saved_place, parent, false)
        return SavedViewHolder(view)
    }

    override fun getItemCount(): Int {
        return savedPlaces.count()
    }

    override fun onBindViewHolder(holder: SavedViewHolder, position: Int) {
        holder.title.text = savedPlaces[position].title
        holder.desc.text = savedPlaces[position].desc

        val imageId = context.resources.getIdentifier(
            savedPlaces[position].image,
            "drawable",
            context.packageName
        )

        holder.image.setImageResource(imageId)
    }
}