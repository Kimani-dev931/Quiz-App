package com.example.quizapp

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.widget.TextView
import com.google.gson.Gson

class ResultActivity : AppCompatActivity() {
    lateinit var textquestion:TextView
    lateinit var quiz:Quiz
    lateinit var txtscore:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        textquestion=findViewById(R.id.txtquestion)
        txtscore=findViewById(R.id.txtscore)
        setUpViews()
    }

    private fun setUpViews() {
       val quizData = intent.getStringExtra("Quiz")
        quiz = Gson().fromJson(quizData,Quiz::class.java)
       calculatescore()
       setAnswerView()
    }

    private fun setAnswerView() {
       val builder = StringBuilder("")
       for (entry in quiz.questions.entries) {
           val question = entry.value
           builder.append("<font color ='#18206F'><b>Question: ${question.description}</b></font><br/><br/>")
           builder.append("<font color ='#009688'>Answer: ${question.answer}</font><br/><br/>")
       }
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            textquestion.text = Html.fromHtml(builder.toString(), Html.FROM_HTML_MODE_COMPACT);
        }else{
            textquestion.text = Html.fromHtml(builder.toString());
        }
    }

    private fun calculatescore() {
        var score = 0
        for(entry in quiz.questions.entries){
            val question =entry.value
            if(question.answer == question.userAnswer){
                score += 10
            }
        }
        txtscore.text = "Your score : $score"

    }
}