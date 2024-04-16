package com.example.todo

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class addnote : AppCompatActivity() {

    lateinit var databaseReference: DatabaseReference
    lateinit var Auth: FirebaseAuth

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_addnote)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
            insets }
        val save=findViewById<Button>(R.id.button)

        save.setOnClickListener {
            val note=findViewById<EditText>(R.id.note).text.toString()
            if(note.isEmpty())
            {
                Toast.makeText(this,"No entry",Toast.LENGTH_SHORT).show()
            }
            else{
                //val n=notes(note)
                val Auth=FirebaseAuth.getInstance()
                val databaseReference=FirebaseDatabase.getInstance().reference

                val curruser=Auth.currentUser

                curruser?.let { user->
                    val notekey=databaseReference.child("User").child(user.uid).child("notes").push().key
                    val n=notes(note,notekey)
                    if(notekey!=null)
                    {databaseReference.child("User").child(user.uid).child("notes").child(notekey).setValue(n)
                        .addOnSuccessListener {
                            Toast.makeText(this,"Saved",Toast.LENGTH_SHORT).show()
                            finish()
                        }.addOnFailureListener{ Toast.makeText(this,"Not Saved",Toast.LENGTH_SHORT).show()}
                }
                }
            }
        }

    }
}