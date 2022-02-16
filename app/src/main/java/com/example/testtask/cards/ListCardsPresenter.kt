package com.example.testtask.cards

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.testtask.APP_ACTIVITY
import com.example.testtask.auth.AuthFragment
import com.example.testtask.cards.detailCard.DetailCardFragment
import com.example.testtask.cards.updateCard.AddCardFragment
import com.example.testtask.model.Card
import com.example.testtask.model.repository.CardRepositoryFirebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

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

    fun getCards() {
        eventListener = object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                val cardList: MutableList<Card> = mutableListOf()

                snapshot.children.map { data ->
                    data.getValue(Card::class.java)?.let { card ->
                        card.id = data.key.toString()
                        cardList.add(card)
                    }
                }

                viewState.setListCard(cardList)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("ErrorListCards", error.message)
            }
        }
        dataBase.getData().addValueEventListener(eventListener)
    }

    fun onExit() {
        dataBase.disconnect(eventListener)
        APP_ACTIVITY.signOut()
        APP_ACTIVITY.launchFragment(AuthFragment())
    }

}