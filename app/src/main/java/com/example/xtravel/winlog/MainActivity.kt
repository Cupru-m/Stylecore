package com.example.app.winlog

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.app.R
import com.example.app.winmain.ItemsActivity
import com.example.xtravel.R
import kotlin.math.log

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedExistAuth = getSharedPreferences("user_already_started", MODE_PRIVATE)
        val isLoggedIn = sharedExistAuth.getBoolean("isLoggedIn", false)

        if (isLoggedIn) {
            val intent = Intent(this, ItemsActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            setContentView(R.layout.activity_main)
        }



        val userLogin: EditText = findViewById(R.id.user_id);
        val userEmail: EditText = findViewById(R.id.email_id);
        val userPassword: EditText = findViewById(R.id.password_id);
        val buttonRegistry: Button = findViewById(R.id.button_reg);
        val linkToAuth: Button = findViewById(R.id.link_to_auth);

        linkToAuth.setOnClickListener{
            val intent = Intent(this, AuthActivity::class.java)
            startActivity(intent)
        }

        buttonRegistry.setOnClickListener {
            val login = userLogin.text.toString().trim()
            val email = userEmail.text.toString().trim()
            val password = userPassword.text.toString().trim()

            if (login == "" || email == "" || password == "" || password.length < 8) {
                if (password.length < 8) {
                    Toast.makeText(this, "Пароль должен быть не менее 8 символов!", Toast.LENGTH_LONG).show()
                }
                Toast.makeText(this, "Пожалуйста, заполните все поля!", Toast.LENGTH_LONG).show()
            }
            else {

                val db = DbHelper(this)
                if (db.isUserExists(login)) {
                    Toast.makeText(this, "Пользователь с таким логином уже существует!", Toast.LENGTH_LONG).show()
                } else {

                    val user = User(login, email, password)
                    db.addUser(user)
                    Toast.makeText(this, "Пользователь $login добавлен", Toast.LENGTH_LONG).show()

                    userLogin.text.clear()
                    userEmail.text.clear()
                    userPassword.text.clear()
                }

            }
        }

    }
}