package com.amod.kotlinquotes.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.amod.kotlinquotes.R
import com.amod.kotlinquotes.model.Quote
import com.amod.kotlinquotes.viewmodel.QuoteViewModal
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.json.JSONException
import org.json.JSONObject


class MainActivity : AppCompatActivity(), NoteClickInterface, NoteClickDeleteInterface {

    //on below line we are creating a variable for our recycler view, exit text, button and viewmodal.
    lateinit var viewModal: QuoteViewModal
    lateinit var notesRV: RecyclerView
    lateinit var addFAB: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //on below line we are initializing all our variables.
        notesRV = findViewById(R.id.notesRV)
        addFAB = findViewById(R.id.idFAB)
        //on below line we are setting layout manager to our recycler view.
        notesRV.layoutManager = LinearLayoutManager(this)
        //on below line we are initializing our adapter class.
        val noteRVAdapter = QuoteAdapter(this, this, this)
        //on below line we are setting adapter to our recycler view.
        notesRV.adapter = noteRVAdapter
        viewModal = ViewModelProvider(
            this, ViewModelProvider.AndroidViewModelFactory.getInstance(
                application
            )
        ).get(
            QuoteViewModal::class.java
        )
        //on below line we are calling all notes method from our view modal class to observer the changes on list.
        viewModal.allQuotes.observe(this, Observer { list ->
            list?.let {
                //on below line we are updating our list.
                noteRVAdapter.updateList(it)
            }
        })
        onInsertJsonItem()
        addFAB.setOnClickListener {
            //adding a click listner for fab button and opening a new intent to add a new note.
            val intent = Intent(this, AddEditNoteActivity::class.java)
            startActivity(intent)
            this.finish()
        }
    }

    override fun onNoteClick(quote: Quote) {
        //opening a new intent and passing a data to it.
        val intent = Intent(this, AddEditNoteActivity::class.java)
        intent.putExtra("noteType", "Edit")
        intent.putExtra("noteTitle", quote.auther)
        intent.putExtra("noteDescription", quote.quote)
        intent.putExtra("noteId", quote.id)
        startActivity(intent)
        this.finish()
    }


    override fun onDeleteIconClick(quote: Quote) {
        //in on note click method we are calling delete method from our viw modal to delete our not.
        viewModal.deleteNote(quote)
        //displaying a toast message
        Toast.makeText(this, "${quote.quote} Deleted", Toast.LENGTH_LONG).show()
    }
    private fun onInsertJsonItem() {

        val fileInString: String =
            applicationContext.assets.open("quotes.json").bufferedReader().use { it.readText() }
        try {
            val mypref_ = getSharedPreferences("mypref", MODE_PRIVATE)
            val inserted = mypref_.getBoolean("Inserted", false)
            Log.e("inserted","is===>"+inserted);
            if (!inserted)
            {
                Log.e("inserted","Going   is===>");
                val obj = JSONObject(fileInString)
                val userArray = obj.getJSONArray("quotes")
                for (i in 0 until userArray.length()) {
                    val userDetail = userArray.getJSONObject(i)
                    viewModal.addNote(Quote(userDetail.getString("author"), userDetail.getString("quote"), "currentDateAndTime"))
                }
                val mypref_ = getSharedPreferences("mypref", MODE_PRIVATE)
                val editor = mypref_.edit()
                editor.putBoolean("Inserted", true)
                editor.apply()
            }
        }
        catch (e: JSONException) {
            e.printStackTrace()
        }
    }

}