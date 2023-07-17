package com.example.quizapp


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class SignInActivity : AppCompatActivity() {

    lateinit var firebaseAuth:FirebaseAuth
    lateinit var email:EditText
    lateinit var password:EditText
    lateinit var btnsignin:Button
    lateinit var textview:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        firebaseAuth= FirebaseAuth.getInstance()

        email=findViewById(R.id.email1)
        password=findViewById(R.id.pass1)
        btnsignin=findViewById(R.id.btnSignin)
        textview=findViewById(R.id.tvsignin)

        btnsignin.setOnClickListener {
            signin()
        }
        textview.setOnClickListener {
            val intent= Intent(this,SignUpActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
    private fun signin(){
        val email=email.text.toString()
        val pass=password.text.toString()
        if(email.isBlank() || pass.isBlank() ){
            Toast.makeText(this,"Email and Password can't be blank",Toast.LENGTH_SHORT).show()
            return
        }

        firebaseAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(this){
            if(it.isSuccessful){
                Toast.makeText(this,"You logged in successfully",Toast.LENGTH_SHORT).show()
                val intent= Intent(this,MainActivity::class.java)
                startActivity(intent)
                finish()
            }
            else{
                Toast.makeText(this,"Logging in failed",Toast.LENGTH_SHORT).show()
            }
        }
    }
}