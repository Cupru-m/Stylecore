package com.example.app.winsaved

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app.R
import com.example.app.winmain.AccountActivity

class SavedPlacesActivity : AppCompatActivity() {

    private lateinit var savedAdapter: SavedAdapter
    private lateinit var savedPlaceList: MutableList<SavedPlaces>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_saved_places)


        val sharedPreferences = getSharedPreferences("unique_user_prefs", MODE_PRIVATE)
        val userLogin = sharedPreferences.getString("USER_LOGIN", "")

        if (!userLogin.isNullOrEmpty()) {
            val savedPlacesDbHelper = SavedPlacesDbHelper(this)

            savedPlaceList = savedPlacesDbHelper.getFavoritePlaceByLogin(userLogin)

            val savedList: RecyclerView = findViewById(R.id.savedList)
            val savedPlaces = arrayListOf<SavedPlaces>()

            savedPlaces.addAll(savedPlaceList)

            savedList.layoutManager = LinearLayoutManager(this)
            savedList.adapter = SavedAdapter(savedPlaces, this)
        }


        val buttonGoToAccount: Button = findViewById(R.id.btnBackToAccountFromSaved)
        buttonGoToAccount.setOnClickListener {
            val intent = Intent(this, AccountActivity::class.java)
            startActivity(intent)
        }
    }
}