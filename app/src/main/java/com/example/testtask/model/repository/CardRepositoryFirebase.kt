package com.example.testtask.model.repository

import android.annotation.SuppressLint
import android.net.Uri
import android.util.Log
import com.example.testtask.model.Card
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import durdinapps.rxfirebase2.RxFirebaseDatabase
import durdinapps.rxfirebase2.RxFirebaseStorage

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

    @SuppressLint("CheckResult")
    override fun add(card: Card) :String {
        card.id = System.currentTimeMillis().toString()
        RxFirebaseDatabase.setValue(database.child(card.id.toString()), card)
            .subscribe(
                {
                    Log.e("Success", "Add Card")
                },
                {
                    error -> Log.e("Error",error.message.toString())
                }
            )

        return card.id.toString()
    }

    @SuppressLint("CheckResult")
    override fun uploadImage(data: Uri?, id: String) {
        data?.let{ uri ->
            val mRef = storageRef.child("${System.currentTimeMillis()}_image")

            RxFirebaseStorage.putFile(mRef, uri)
                .filter {
                    result -> result.task.isSuccessful
                }
                .map {
                    RxFirebaseStorage.getDownloadUrl(mRef)
                        .map {
                            url ->  RxFirebaseDatabase.setValue(database.child(id).child("image"), url.toString())
                            .subscribe(
                                {
                                    Log.e("Success", "Get Url Storage")

                                },
                                {
                                    error -> Log.e("Error",error.message.toString())
                                }
                            )
                        }
                        .subscribe(
                            {
                                Log.e("Success", "Get Url Storage")
                            },
                            {
                                error -> Log.e("Error",error.message.toString())
                            }
                        )

                }
                .subscribe(
                    {
                        Log.e("Success", "Update Image Storage")
                    },
                    {
                        error -> Log.e("Error",error.message.toString())
                    }
                )

        }
    }

    override fun getData(): DatabaseReference = database

    override fun getInfoCardId(id: String): DatabaseReference = database.child(id)

    @SuppressLint("CheckResult")
    override fun update(card: Card) {
        RxFirebaseDatabase.setValue(database.child(card.id.toString()), card)
            .subscribe(
                {
                    Log.e("Success", "Update Card")
                },
                {
                    error -> Log.e("Error",error.message.toString())
                }
            )
    }

    override fun connect(tableName: String) = Firebase.database.reference.child(tableName)

    override fun disconnect(eventListener: ValueEventListener) = database.removeEventListener(eventListener)

}