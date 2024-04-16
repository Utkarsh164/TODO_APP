package com.example.todo

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.play.integrity.internal.t
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity2 : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    lateinit var auth:FirebaseAuth
    lateinit var databaseReference: DatabaseReference


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main2)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
            insets }

        val btn=findViewById<ImageView>(R.id.add)
        btn.setOnClickListener {
            val i=Intent(this,addnote::class.java)
            startActivity(i) }

        auth=FirebaseAuth.getInstance()
        databaseReference=FirebaseDatabase.getInstance().reference
        val a= ArrayList<notes>()

        recyclerView = findViewById(R.id.list)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val ad = MyAdaptar(this, a)
        recyclerView.adapter = ad


        val cur=auth.currentUser
        cur?.let {
            val notereference= databaseReference.child("User").child(it.uid).child("notes")
            notereference.addValueEventListener(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {

                    a.clear()

                    for(i in snapshot.children)
                    {
                        val s=i.getValue(notes::class.java)
                        s?.let {
                            a.add(it)
                        }}
                        ad.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {}
            }

            )





        }


        



    }
}