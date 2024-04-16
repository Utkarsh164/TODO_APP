package com.example.todo
import android.app.Activity
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MyAdaptar(val context:Activity,val a:ArrayList<notes>):RecyclerView.Adapter<MyAdaptar.MyViewHolder>() {


    lateinit var firebaseAuth:FirebaseAuth
    lateinit var databaseReference: DatabaseReference



    class MyViewHolder(val itemview: View):RecyclerView.ViewHolder(itemview) {
        val tex=itemview.findViewById<TextView>(R.id.textView3)

        val deleteButton=itemview.findViewById<Button>(R.id.del)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdaptar.MyViewHolder {
        val inf=LayoutInflater.from(parent.context).inflate(R.layout.box,parent,false)
        return MyViewHolder(inf)
    }

    override fun onBindViewHolder(holder: MyAdaptar.MyViewHolder, position: Int) {
        val cur=a[position]
        holder.tex.text=cur.note

       //ye delete wala part complete nahi hai sara ka sara user udd raha hai
        holder.deleteButton.setOnClickListener {
            // Delete data from Firebase
            firebaseAuth = FirebaseAuth.getInstance()
            databaseReference = FirebaseDatabase.getInstance().reference

            val curUser = firebaseAuth.currentUser
            curUser?.let { user ->
                val noteId = cur.id
                if (noteId != null) {
                    val noteRef = databaseReference.child("User").child(user.uid).child("notes").child(noteId)
                    noteRef.removeValue().addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            // Data deleted successfully from Firebase, now remove from the list
                            a.remove(cur)
                            notifyDataSetChanged()
                        }

                    }
                }

            }
        }
    }

    override fun getItemCount(): Int {
        return a.size
    }
}


