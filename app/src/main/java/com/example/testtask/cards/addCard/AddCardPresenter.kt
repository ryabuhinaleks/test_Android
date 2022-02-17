package com.example.testtask.cards.addCard

import android.net.Uri
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.testtask.model.Card
import com.example.testtask.model.repository.CardRepositoryFirebase

@InjectViewState
class AddCardPresenter : MvpPresenter<AddCardView>() {

    private var dataBase: CardRepositoryFirebase = CardRepositoryFirebase()
    private var imageURI : Uri? = null

    fun createCard (card: Card) {
        val idNewCard = dataBase.add(card)

        if (imageURI != null) {
            dataBase.uploadImage(imageURI, idNewCard)
        }
    }

    fun setNewImage(data: Uri?) {
        viewState.setImage(data)
        imageURI = data
    }
}