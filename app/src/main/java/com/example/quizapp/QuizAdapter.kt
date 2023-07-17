package com.example.quizapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class QuizAdapter (val context: Context,val quizzes : List<Quiz>):RecyclerView.Adapter<QuizAdapter.QuizViewHolder>(){



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.quiz_item,parent,false)
        return QuizViewHolder(view)
    }

    override fun getItemCount(): Int {
        return  quizzes.size
    }

    override fun onBindViewHolder(holder: QuizViewHolder, position: Int) {
        holder.textviewtitle.text = quizzes[position].title
        holder.itemView.setOnClickListener {
            Toast.makeText(context,quizzes[position].title,Toast.LENGTH_SHORT).show()
            val intent = Intent(context,QuestionActivity::class.java)
            intent.putExtra("DATE",quizzes[position].title)
            context.startActivity(intent)
        }
    }
    inner class QuizViewHolder(itemView: View ):RecyclerView.ViewHolder(itemView){
        var textviewtitle :TextView = itemView.findViewById(R.id.quiztitle)
        var iconview :ImageView = itemView.findViewById(R.id.quizicon)
        var cardcontainer :CardView = itemView.findViewById(R.id.cardcontainer)
    }
}