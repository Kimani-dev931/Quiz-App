package com.example.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.DatePicker
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var actionbardrawertoggle:ActionBarDrawerToggle
    lateinit var drawerLayout: DrawerLayout
    lateinit var appbar:androidx.appcompat.widget.Toolbar
    lateinit var recyclerview:RecyclerView
    lateinit var navview:NavigationView
    lateinit var adapter: QuizAdapter
    lateinit var datepicker:FloatingActionButton
    private var quizlist = mutableListOf<Quiz>()
    lateinit var firestore:FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        appbar=findViewById(R.id.AppBar)
        drawerLayout=findViewById(R.id.mainDrawer)
        recyclerview=findViewById(R.id.recyclerview)
        datepicker=findViewById(R.id.btndatepicker)
        navview=findViewById(R.id.navigationview)
        setSupportActionBar(appbar)
        setupviews()
        setupdatepicker()
        setupFirestore()

    }

    private fun setupdatepicker() {
          datepicker.setOnClickListener {
              val datePicker=MaterialDatePicker.Builder.datePicker().build()
              datePicker.show(supportFragmentManager,"DatePicker")
              datePicker.addOnPositiveButtonClickListener {
                  Log.d("DATEPICKER",datePicker.headerText)
                  val dateFormatter = SimpleDateFormat("dd/mm/yyyy")
                  val date = dateFormatter.format(Date(it))
                  val intent = Intent(this,QuestionActivity::class.java)
                  intent.putExtra("DATE",date)
                  startActivity(intent)
              }
              datePicker.addOnNegativeButtonClickListener {
                  Log.d("DATEPICKER",datePicker.headerText)
              }
              datePicker.addOnCancelListener {
                  Log.d("DATEPICKER","Date Picker Cancelled")
              }
          }
    }



    fun setupviews(){
        setupFirestore()
        setupDrawerLayout()
        setupRecyclerview()
        setupdatepicker()
    }

    private fun setupFirestore() {
         firestore = FirebaseFirestore.getInstance()
         val collectionReference=firestore.collection("quizzes")
         collectionReference.addSnapshotListener { value, error ->
             if(value == null || error != null){
                 Toast.makeText(this,"Error fetching data",Toast.LENGTH_SHORT).show()
                 return@addSnapshotListener
             }
             Log.d("DATA",value.toObjects(Quiz::class.java).toString())
             quizlist.clear()
             quizlist.addAll(value.toObjects(Quiz::class.java))
             adapter.notifyDataSetChanged()
         }
    }

    private fun setupRecyclerview() {
        adapter = QuizAdapter(this,quizlist)
        recyclerview.layoutManager = GridLayoutManager(this,2)
        recyclerview.adapter = adapter
    }

    fun setupDrawerLayout(){

        actionbardrawertoggle= ActionBarDrawerToggle(this,drawerLayout,R.string.app_name,R.string.app_name)
        actionbardrawertoggle.syncState()
        navview.setNavigationItemSelectedListener {
            val intent =Intent(this,ProfileActivity::class.java)
            startActivity(intent)
            drawerLayout.closeDrawers()
            true
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(actionbardrawertoggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}