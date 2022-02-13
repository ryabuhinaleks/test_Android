package com.example.testtask.cards.addCard

import android.net.Uri
import android.widget.EditText
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.testtask.APP_ACTIVITY
import com.example.testtask.R
import com.example.testtask.databinding.FragmentUpdateCardBinding
import com.example.testtask.model.Card
import com.example.testtask.model.repository.CardRepositoryFirebase

@InjectViewState
class AddCardPresenter : MvpPresenter<AddCardView>() {

    private var dataBase: CardRepositoryFirebase = CardRepositoryFirebase()
    private var imageURI : Uri? = null

    fun createCard (binding: FragmentUpdateCardBinding) :Boolean {
        with(binding) {
            if (APP_ACTIVITY.showEmptyField(price, area, place)) {
                val idNewCard = dataBase.add(getDataCard(binding))

                if (imageURI != null) {
                    dataBase.uploadImage(imageURI, idNewCard)
                }
                return true
            }
            return false
        }
    }

    private fun getDataCard(binding: FragmentUpdateCardBinding): Card {
        return with(binding) {
            Card(
                price = price.text.toString().toLong(),
                area = area.text.toString().toInt(),
                place = place.text.toString(),

                count = count.text.toString().toIntOrNull(),
                price2 = price2.text.toString().toIntOrNull(),
                floor = floor.text.toString().toIntOrNull()
            )
        }
    }

    fun setNewImage(data: Uri?) {
        viewState.setImage(data)
        imageURI = data
    }
}