package com.example.quizapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson

class QuestionActivity : AppCompatActivity() {
    lateinit var previous:Button
    lateinit var next:Button
    lateinit var submit:Button
    lateinit var description:TextView
    lateinit var optionlist:RecyclerView
    var quizzes : MutableList<Quiz>? = null
    var questions : MutableMap<String,Question>? = null
    var index = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)
        description=findViewById(R.id.description)
        optionlist=findViewById(R.id.optionlist)
        previous=findViewById(R.id.btnprevious)
        next=findViewById(R.id.btnnext)
        submit=findViewById(R.id.btnsubmit)
        setUpFirestore()
        setupEventListener()
    }

    private fun setupEventListener() {
       previous.setOnClickListener {
           index--
           bindviews()
       }
       next.setOnClickListener {
           index++
           bindviews()
       }
        submit.setOnClickListener {
            Log.d("FINALQUIZ",questions.toString())

            val intent = Intent(this,ResultActivity::class.java)
            val json = Gson().toJson(quizzes!![0])
            intent.putExtra("Quiz",json)
            startActivity(intent)
        }
    }

    private fun setUpFirestore() {
        val firestore = FirebaseFirestore.getInstance()
        var date=intent.getStringExtra("DATE")
        if(date != null){
            firestore.collection("quizzes").whereEqualTo("title",date)
                .get()
                .addOnSuccessListener {
                    if (it != null && !it.isEmpty){
                        quizzes = it.toObjects(Quiz::class.java)
                        questions = quizzes!![0].questions
                        bindviews()

                    }

                }
        }

    }

    private fun bindviews() {

       previous.visibility = View.GONE
       next.visibility= View.GONE
       submit.visibility = View.GONE
       if(index == 1){
           next.visibility = View.VISIBLE
       }
       else if(index == questions!!.size){
           submit.visibility = View.VISIBLE
           previous.visibility = View.VISIBLE
       }
       else{
           previous.visibility = View.VISIBLE
           next.visibility= View.VISIBLE
       }
       val question = questions!!["question$index"]
        question?.let{
            description.text = it.description
            val optionAdapter = OptionAdapter(this,it)
            optionlist.layoutManager= LinearLayoutManager(this)
            optionlist.adapter = optionAdapter
            optionlist.setHasFixedSize(true)
        }

    }
}