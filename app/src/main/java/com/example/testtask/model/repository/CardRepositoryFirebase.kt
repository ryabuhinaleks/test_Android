package com.example.testtask.model.repository

import android.net.Uri
import com.example.testtask.model.Card
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

const val tableName = "Card"

interface Source {

    fun connect (tableName: String) :DatabaseReference

    fun disconnect (eventListener: ValueEventListener)
}

interface DB {

    fun add(card: Card) : String

    fun update(card: Card)

    fun getData(): DatabaseReference

    fun uploadImage(data: Uri?, id: String)

    fun getInfoCardId(id: String): DatabaseReference

}

class CardRepositoryFirebase : DB, Source {

    private var database: DatabaseReference
    private var storageRef: StorageReference;

    init {
        database = connect(tableName)
        storageRef = FirebaseStorage.getInstance().reference.child("${tableName}IMG")
    }

    override fun add(card: Card) :String {
        card.id = System.currentTimeMillis().toString()
        database.child(card.id.toString()).setValue(card)
        return card.id.toString()
    }

    override fun uploadImage(data: Uri?, id: String) {
        var mRef = storageRef.child("${System.currentTimeMillis()}_image")
        mRef.putFile(data!!).addOnCompleteListener {
            if (it.isSuccessful) {
                mRef.downloadUrl.addOnCompleteListener {
                    database.child(id).child("image").setValue(it.result.toString())
                }
            }
        }
    }

    override fun getData(): DatabaseReference = database

    override fun getInfoCardId(id: String): DatabaseReference = database.child(id)

    override fun update(card: Card) {
        database.child(card.id.toString()).setValue(card)
    }

    override fun connect(tableName: String) = Firebase.database.reference.child(tableName)

    override fun disconnect(eventListener: ValueEventListener) = database.removeEventListener(eventListener)

}