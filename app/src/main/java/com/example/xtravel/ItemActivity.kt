package com.example.app

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.app.winmain.ItemsActivity
import com.example.app.winsaved.SavedPlaces
import com.example.app.winsaved.SavedPlacesDbHelper

class ItemActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item)

        val sharedPreferences = getSharedPreferences("unique_user_prefs", MODE_PRIVATE)
        val userLogin = sharedPreferences.getString("USER_LOGIN", "") ?: ""

        val savedPlacesDbHelper = SavedPlacesDbHelper(this)
        val title: TextView = findViewById(R.id.item_list_title1)
        val text: TextView = findViewById(R.id.item_list_text)

        title.text = intent.getStringExtra("itemTitle")

        val image: ImageView = findViewById(R.id.item_image)

        val imageName = intent.getStringExtra("itemImage") ?: ""

        val imageId = resources.getIdentifier(
            imageName,
            "drawable",
            packageName
        )

        if (imageId != -1) {
            image.setImageResource(imageId)
        }
        text.text = intent.getStringExtra("itemText")



        val backButton: Button = findViewById(R.id.dislike_button)

        backButton.setOnClickListener {
            val intent = Intent(this, ItemsActivity::class.java)
            startActivity(intent)
        }

        val addToFavoriteButton: Button = findViewById(R.id.like_button)
        addToFavoriteButton.setOnClickListener {
            val etImage = intent.getStringExtra("itemImage") ?: ""
            val etTitle = intent.getStringExtra("itemTitle") ?: ""
            val etDesc = intent.getStringExtra("itemDesc") ?: ""

            if (savedPlacesDbHelper.isPlaceFavoriteForUser(userLogin, etTitle)) {
                Toast.makeText(this, "Место уже добавлено в избранное", Toast.LENGTH_LONG).show()
            }
            else {
                val newFavoritePlace = SavedPlaces(login = userLogin, image = etImage, title = etTitle, desc = etDesc)
                val newFavPlaceId = savedPlacesDbHelper.addFavoritePlaceForUser(newFavoritePlace)

                if (newFavPlaceId != null) {
                    val favPlaceWithId = newFavoritePlace.copy(id = newFavPlaceId)
                }

                Toast.makeText(this, "Новое место добавлено в избранное", Toast.LENGTH_LONG).show()
            }
        }

    }
}