package com.example.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : AppCompatActivity() {
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var Email:EditText
    lateinit var Password:EditText
    lateinit var confirmpass:EditText
    lateinit var btnSignUp:Button
    lateinit var textfield:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        firebaseAuth = FirebaseAuth.getInstance()

        Email=findViewById(R.id.Email)
        Password=findViewById(R.id.pass)
        confirmpass=findViewById(R.id.confirmpass)
        btnSignUp=findViewById(R.id.btnsignup)
        textfield=findViewById(R.id.tvsignup)

        btnSignUp.setOnClickListener {
            signupuser()
        }
        textfield.setOnClickListener {
            val intent= Intent(this,SignInActivity::class.java)
            startActivity(intent)
            finish()
        }


    }
    private fun signupuser(){
        val email= Email.text.toString()
        val password=Password.text.toString()
        val confpass=confirmpass.text.toString()

        if(email.isBlank() || password.isBlank() || confpass.isBlank()){
            Toast.makeText(this,"Email and Password can't be blank",Toast.LENGTH_SHORT).show()
            return
        }
        if(password != confpass){
            Toast.makeText(this,"Password and confirm Password do not match", Toast.LENGTH_SHORT).show()
            return
        }


        firebaseAuth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener(this){
                if(it.isSuccessful){
                    Toast.makeText(this,"Signed Up successfully",Toast.LENGTH_SHORT).show()
                    val intent= Intent(this,SignInActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                else{
                    Toast.makeText(this,"Error creating user",Toast.LENGTH_SHORT).show()
                }
            }


    }
}