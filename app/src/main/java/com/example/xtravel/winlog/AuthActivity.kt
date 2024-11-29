package com.example.app.winlog

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.app.R
import com.example.app.winmain.ItemsActivity

class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        val userLogin: EditText = findViewById(R.id.user_login_auth);
        val userPassword: EditText = findViewById(R.id.user_password_auth);
        val buttonRegistry: Button = findViewById(R.id.button_auth);
        val linkToReg: TextView = findViewById(R.id.link_to_reg)

        linkToReg.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        buttonRegistry.setOnClickListener {
            val login = userLogin.text.toString().trim()
            val password = userPassword.text.toString().trim()

            if (login == "" || password == "") {
                Toast.makeText(this, "Пожалуйста, заполните все поля!", Toast.LENGTH_LONG).show()
            }
            else {
                val db = DbHelper(this)
                val isAuth = db.getUser(login, password)

                if (isAuth) {
                    Toast.makeText(this, "Вход $login выполнен успешно!", Toast.LENGTH_LONG).show()
                    userLogin.text.clear()
                    userPassword.text.clear()

                    val sharedPreferences = getSharedPreferences("unique_user_prefs", MODE_PRIVATE)
                    sharedPreferences.edit().clear().apply()
                    val editor = sharedPreferences.edit()
                    editor.putString("USER_LOGIN", login)
                    editor.apply()

                    val sharedExistAuth = getSharedPreferences("user_already_started", MODE_PRIVATE)
                    sharedExistAuth.edit().clear().apply()
                    val editorStart = sharedExistAuth.edit()
                    editorStart.putBoolean("isLoggedIn", true)
                    editorStart.apply()

                    val intent = Intent(this, ItemsActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Пользователь $login не авторизован!", Toast.LENGTH_LONG).show()
                }

            }
        }
    }
}