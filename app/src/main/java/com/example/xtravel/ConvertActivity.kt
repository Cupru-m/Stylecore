package com.example.app

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.app.winmain.AccountActivity

class ConvertActivity : AppCompatActivity() {

    external fun addTwoNumbers(usd: Int): Float

    companion object {
        init {
            System.loadLibrary("native-lib")
        }
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_convert)


        val btnConvert = findViewById<Button>(R.id.btnConvert)
        btnConvert.setOnClickListener {
            val etUSD = findViewById<EditText>(R.id.etUSD)
            val usd = etUSD.text.toString().trim().toIntOrNull() ?: 0


            val sum = addTwoNumbers(usd)
            showConvertedInformation(sum)
        }

        val btnGoBack = findViewById<Button>(R.id.btn_go_back_from_convert)
        btnGoBack.setOnClickListener {
            val intent = Intent(this, AccountActivity::class.java)
            startActivity(intent)
        }
    }

    private fun showConvertedInformation(message: Float) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_funny_message, null)
        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .create()

        dialogView.findViewById<TextView>(R.id.dialog_message).text = message.toString() + " BYN"

        dialogView.findViewById<Button>(R.id.ok_button).setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }
}