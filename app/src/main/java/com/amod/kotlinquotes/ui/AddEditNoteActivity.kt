package com.amod.kotlinquotes.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.amod.kotlinquotes.R
import com.amod.kotlinquotes.model.Quote
import com.amod.kotlinquotes.viewmodel.QuoteViewModal
import java.text.SimpleDateFormat
import java.util.*

class AddEditNoteActivity : AppCompatActivity() {
    //on below line we are creating variables for our UI components.
    lateinit var noteTitleEdt: EditText
    lateinit var noteEdt: EditText
    lateinit var saveBtn: Button

    //on below line we are creating variable for viewmodal and and integer for our note id.
    lateinit var viewModal: QuoteViewModal
    var noteID = -1;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_quote)
        //on below line we are initlaiing our view modal.
        viewModal = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(QuoteViewModal::class.java)
        //on below line we are initializing all our variables.
        noteTitleEdt = findViewById(R.id.idEdtNoteName)
        noteEdt = findViewById(R.id.idEdtNoteDesc)
        saveBtn = findViewById(R.id.idBtn)

        //on below line we are getting data passsed via an intent.
        val noteType = intent.getStringExtra("noteType")
        if (noteType.equals("Edit")) {
            //on below line we are setting data to edit text.
            val noteTitle = intent.getStringExtra("noteTitle")
            val noteDescription = intent.getStringExtra("noteDescription")
            noteID = intent.getIntExtra("noteId", -1)
            saveBtn.setText("Update Quote")
            noteTitleEdt.setText(noteTitle)
            noteEdt.setText(noteDescription)
        } else {
            saveBtn.setText("Save Quote")
        }
        saveBtn.setOnClickListener {
            //on below line we are getting title and desc from edit text.
            val noteTitle = noteTitleEdt.text.toString()
            val noteDescription = noteEdt.text.toString()
            if (noteType.equals("Edit")) {
                if (noteTitleEdt.text.isEmpty()|| noteEdt.text.isEmpty()) {
                    Toast.makeText(this, "Please Enter Quote ", Toast.LENGTH_LONG).show()
                } else if (noteTitle.isNotEmpty() && noteDescription.isNotEmpty()) {
                    val sdf = SimpleDateFormat("dd MMM, yyyy - HH:mm")
                    val currentDateAndTime: String = sdf.format(Date())
                    val updatedNote = Quote(noteTitle, noteDescription, currentDateAndTime)
                    updatedNote.id = noteID
                    viewModal.updateNote(updatedNote)
                    Toast.makeText(this, "Quote Updated..", Toast.LENGTH_LONG).show()
                    startActivity(Intent(applicationContext, MainActivity::class.java))
                    this.finish()
                }
            } else {
                    if (noteTitleEdt.text.isEmpty()|| noteEdt.text.isEmpty()) {
                        Toast.makeText(this, "Please Enter Quote ", Toast.LENGTH_LONG).show()
                    } else if (noteTitle.isNotEmpty() && noteDescription.isNotEmpty()) {
                        val sdf = SimpleDateFormat("dd MMM, yyyy - HH:mm")
                        val currentDateAndTime: String = sdf.format(Date())
                        //if the string is not empty we are calling a add note  method to add data to our room database.
                        viewModal.addNote(Quote(noteTitle, noteDescription, currentDateAndTime))
                        Toast.makeText(this, "Quote Added", Toast.LENGTH_LONG).show()
                        startActivity(Intent(applicationContext, MainActivity::class.java))
                        this.finish()
                    }


            }
        }
    }


}