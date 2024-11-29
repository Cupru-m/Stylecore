package com.example.app.winmain

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.example.app.ConvertActivity
import com.example.app.winlog.AuthActivity
import com.example.app.R
import com.example.app.winsaved.SavedPlacesActivity

class AccountActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)

        val buttonGoToAccount: ImageButton = findViewById(R.id.account_button_acc)
        val buttonGoToPictures: ImageButton = findViewById(R.id.picture_button_acc)

        buttonGoToPictures.setOnClickListener {
            val intent = Intent(this, ItemsActivity::class.java)
            startActivity(intent)
        }

        val buttonGoToConvert: Button = findViewById(R.id.link_to_convert)

        buttonGoToConvert.setOnClickListener {
            val intent = Intent(this, ConvertActivity::class.java)
            startActivity(intent)
        }

        val buttonGoToBasket: ImageButton = findViewById(R.id.basket_button_acc)
        buttonGoToBasket.setOnClickListener{
            val intent = Intent(this, SavedPlacesActivity::class.java)
            startActivity(intent)
        }

        val buttonLogOut: Button = findViewById(R.id.link_to_log_out)
        buttonLogOut.setOnClickListener {
            val sharedExistAuth = getSharedPreferences("user_already_started", MODE_PRIVATE)
            val editorStart = sharedExistAuth.edit()
            editorStart.putBoolean("isLoggedIn", false)
            editorStart.apply()
            val intent = Intent(this, AuthActivity::class.java)

            startActivity(intent)
        }

    }

}