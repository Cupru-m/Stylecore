package com.example.app.winmain

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.GestureDetector
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app.Item
import com.example.app.ItemsAdapter
import com.example.app.R
import com.example.app.winsaved.SavedPlacesActivity

class ItemsActivity : AppCompatActivity() {

    private lateinit var gestureDetector: GestureDetector
    private var lastTapTime: Long = 0
    private var tapCount: Int = 0
    private val TAP_THRESHOLD: Long = 300

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_items)

        val rootLayout: View = findViewById(R.id.main)

        val sharedPreferences = getSharedPreferences("unique_user_prefs", MODE_PRIVATE)
        val userLogin = sharedPreferences.getString("USER_LOGIN", "")

        rootLayout.setOnClickListener {
            val currentTime = System.currentTimeMillis()
            if (currentTime - lastTapTime < TAP_THRESHOLD) {
                tapCount++

                if (tapCount >= 3) {
                    navigateToNextActivity()
                }
            } else {
                tapCount = 1
            }

            lastTapTime = currentTime
        }

        val buttonGoToAccount: ImageButton = findViewById(R.id.account_button)

        val buttonGoToBasket: ImageButton = findViewById(R.id.basket_button)

        buttonGoToAccount.setOnClickListener {
            val intent = Intent(this, AccountActivity::class.java)
            startActivity(intent)
        }

        buttonGoToBasket.setOnClickListener{
            val intent = Intent(this, SavedPlacesActivity::class.java)
            startActivity(intent)
        }


        buttonGoToAccount.setOnLongClickListener{
            if (!userLogin.isNullOrEmpty()) {
                showFunnyMessage(userLogin)
            }
            true
        }

        val itemsList: RecyclerView = findViewById(R.id.itemsList)
        val items = arrayListOf<Item>()

        items.add(Item(1, "sofa", "Уборка дивана", "40 BYN", "Уборка дивана\n"))
        items.add(Item(2, "window_cleaning", "Чистка окон", "60 BYN", "Чистка окон"))
        items.add(Item(3, "stain", "Выведение пятен", "20 BYN", "Выведение пятен"))

        itemsList.layoutManager = LinearLayoutManager(this)
        itemsList.adapter = ItemsAdapter(items, this)
    }

    companion object {
        private const val SWIPE_THRESHOLD = 100
    }

    private fun showFunnyMessage(message: String) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_funny_message, null)
        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .create()

        dialogView.findViewById<TextView>(R.id.dialog_message).text = message

        dialogView.findViewById<Button>(R.id.ok_button).setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun navigateToNextActivity() {
        val intent = Intent(this, AccountActivity::class.java)
        startActivity(intent)
        tapCount = 0
        lastTapTime = 0
    }

}