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
import com.google.firebase.auth.FirebaseUser

class signin : AppCompatActivity() {

    lateinit var firebaseAuth: FirebaseAuth

    public override fun onStart() {
        super.onStart()

        firebaseAuth = FirebaseAuth.getInstance()

        val currentUser: FirebaseUser? = firebaseAuth.currentUser
        if (currentUser != null) {
            val i = Intent(this, MainActivity2::class.java)
            startActivity(i)
            finish()
        }
    }



    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_signin)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
            insets }

        val log=findViewById<Button>(R.id.log)
        log.setOnClickListener {
            val i=Intent(this,Login::class.java)
            startActivity(i) }

        val sin=findViewById<Button>(R.id.button2)
        sin.setOnClickListener {
            val em=findViewById<EditText>(R.id.email).text.toString()
            val pw=findViewById<EditText>(R.id.password).text.toString()
            if(em.isEmpty()||pw.isEmpty())
            {
                Toast.makeText(this,"Fill all Fields",Toast.LENGTH_SHORT).show()
            }
            else
            {
                firebaseAuth=FirebaseAuth.getInstance()
                firebaseAuth.signInWithEmailAndPassword(em,pw)
                    .addOnCompleteListener(this) {
                        if(it.isSuccessful)
                        {
                            val i=Intent(this,MainActivity2::class.java)
                            startActivity(i)
                            finish()
                        }
                        else{
                            Toast.makeText(this,"${it.exception?.message}",Toast.LENGTH_SHORT).show()
                        }
                    }

            }
        }
    }
}