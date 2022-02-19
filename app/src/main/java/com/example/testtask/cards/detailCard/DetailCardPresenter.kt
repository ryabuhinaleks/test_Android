package com.example.testtask.cards.detailCard

import android.annotation.SuppressLint
import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.testtask.model.Card
import com.example.testtask.model.repository.CardRepositoryFirebase
import durdinapps.rxfirebase2.RxFirebaseDatabase

@InjectViewState
class DetailCardPresenter : MvpPresenter<DetailCardView>() {

    private var dataBase: CardRepositoryFirebase = CardRepositoryFirebase()

    @SuppressLint("CheckResult")
    fun getPropertyCard(id: String) {

        RxFirebaseDatabase.observeValueEvent(dataBase.getInfoCardId(id), Card::class.java)
            .filter {
                    card -> card != null
            }
            .subscribe(
                {
                    response -> viewState.setCard(cardInfo = response)
                },
                {
                    error -> Log.e("ErrorListCards", error.message.toString())
                }
            )
    }
}

