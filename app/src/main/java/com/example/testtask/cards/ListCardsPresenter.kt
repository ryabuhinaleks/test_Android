package com.example.testtask.cards

import android.annotation.SuppressLint
import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.testtask.APP_ACTIVITY
import com.example.testtask.auth.AuthFragment
import com.example.testtask.cards.detailCard.DetailCardFragment
import com.example.testtask.cards.updateCard.AddCardFragment
import com.example.testtask.model.Card
import com.example.testtask.model.repository.CardRepositoryFirebase
import com.google.firebase.database.ValueEventListener
import durdinapps.rxfirebase2.RxFirebaseDatabase

@InjectViewState
class ListCardsPresenter : MvpPresenter<ListCardsView>() {

    private var dataBase: CardRepositoryFirebase = CardRepositoryFirebase()
    private lateinit var eventListener: ValueEventListener

    fun createAdapter() = CardsAdapter(object : CardsListener {
        override fun cardDetail(card: Card) {
            card.id?.let { id ->
                APP_ACTIVITY.launchFragment(DetailCardFragment.newInstance(id))
            }
        }
    })

    fun onAddCard() = APP_ACTIVITY.launchFragment(AddCardFragment())

    @SuppressLint("CheckResult")
    fun getCards() {

        RxFirebaseDatabase.observeChildEvent(dataBase.getData(), Card::class.java)
            .map {
                result -> result.value
            }
            .filter {
                card -> card != null
            }
            .subscribe(
                {
                    response -> viewState.setListCard(response)
                },
                {
                    error -> Log.e("ErrorListCards", error.message.toString())
                }
            )
    }

    fun onExit() {
        dataBase.disconnect(eventListener)
        APP_ACTIVITY.signOut()
        APP_ACTIVITY.launchFragment(AuthFragment())
    }

}