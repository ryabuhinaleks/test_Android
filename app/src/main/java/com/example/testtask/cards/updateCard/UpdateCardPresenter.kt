package com.example.testtask.cards.updateCard

import android.net.Uri
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.testtask.model.Card
import com.example.testtask.model.repository.CardRepositoryFirebase

@InjectViewState
class UpdateCardPresenter : MvpPresenter<UpdateCardView>() {

    private var dataBase: CardRepositoryFirebase = CardRepositoryFirebase()
    private var imageURI : Uri? = null

    fun updateCard(card: Card) {
        dataBase.update(card)
        if (imageURI != null && card.id != null) {
            dataBase.uploadImage(imageURI, card.id!!)
        }
    }

    fun updateImage() = imageURI.toString()

    fun setNewImage(data: Uri?) {
        viewState.setImage(data)
        imageURI = data
    }
}