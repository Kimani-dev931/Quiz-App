package com.example.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth

class ProfileActivity : AppCompatActivity() {
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var txtemail:TextView
    lateinit var btnlogout:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        txtemail=findViewById(R.id.textemail)
        btnlogout=findViewById(R.id.btnlogout)
        firebaseAuth= FirebaseAuth.getInstance()
        txtemail.text = firebaseAuth.currentUser?.email

        btnlogout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this,SignInActivity::class.java)
            startActivity(intent)
        }

    }
}