package com.example.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class FrontScreenActivity : AppCompatActivity() {
    lateinit var btngetstarted:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_front_screen)
        btngetstarted=findViewById(R.id.btnget)
        val auth =FirebaseAuth.getInstance()
        if(auth.currentUser != null){
            Toast.makeText(this,"User already logged in!",Toast.LENGTH_SHORT).show()
            redirect("MAIN")
        }
        btngetstarted.setOnClickListener {
            redirect("LOGIN")
        }
    }
    private fun redirect(name:String){
        val intent=when(name){
            "LOGIN" -> Intent(this,SignInActivity::class.java)
            "MAIN" -> Intent(this,MainActivity::class.java)
            else ->throw Exception("no path exists")
        }
        startActivity(intent)
        finish()
    }
}