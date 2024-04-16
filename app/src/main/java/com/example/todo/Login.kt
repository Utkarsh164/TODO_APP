package com.example.todo

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {

    lateinit var firebaseAuth: FirebaseAuth

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
            insets}
        val goo=findViewById<Button>(R.id.googl)
        goo.setOnClickListener {
            Toast.makeText(this,"Work in progress",Toast.LENGTH_SHORT).show()
        }
        val loginit=findViewById<Button>(R.id.log)
        loginit.setOnClickListener {
            val email=findViewById<EditText>(R.id.EmailAddress).text.toString()
            val pword=findViewById<EditText>(R.id.Password).text.toString()
            val rpword=findViewById<EditText>(R.id.editTextTextPassword3).text.toString()

            if(email.isEmpty()||  pword.isEmpty()||rpword.isEmpty())
            {
                Toast.makeText(this, "Please Fill All Field", Toast.LENGTH_SHORT).show()
            }
            else
            {
                if(pword!=rpword)
                {
                    Toast.makeText(this,"Repeat password is not same as password",Toast.LENGTH_SHORT).show()
                }
                else
                {
                    firebaseAuth= FirebaseAuth.getInstance()
                    firebaseAuth.createUserWithEmailAndPassword(email,pword)
                        .addOnCompleteListener(this) {
                            if(it.isSuccessful)
                            {
                                Toast.makeText(this,"Registration Successful",Toast.LENGTH_SHORT).show()
                                val i=Intent(this,MainActivity2::class.java)
                                startActivity(i)
                                finish()
                            }
                            else
                            {
                                Toast.makeText(this,"Registration Failed :${it.exception?.message}",Toast.LENGTH_SHORT).show()
                            }
                        }
                }
            }
        }
    }
}


