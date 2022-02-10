package com.example.testtask.cards.detailCard

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.testtask.model.Card
import com.example.testtask.model.repository.CardRepositoryFirebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

@InjectViewState
class DetailCardPresenter : MvpPresenter<DetailCardView>() {

    private var dataBase: CardRepositoryFirebase = CardRepositoryFirebase()

    fun getPropertyCard(id: String) {
        dataBase.getInfoCardId(id).addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                viewState.setCard(cardInfo = snapshot.getValue(Card::class.java)!!)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("ErrorDetailCard", error.message)
            }
        })
    }
}

