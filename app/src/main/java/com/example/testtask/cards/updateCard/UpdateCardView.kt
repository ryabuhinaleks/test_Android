package com.example.testtask.cards.updateCard

import android.net.Uri
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

@StateStrategyType(value = AddToEndSingleStrategy::class)
interface UpdateCardView : MvpView {

    fun getImage()

    fun setImage(data: Uri?)

    fun updateDataCard()

    fun updateUI()

    fun showImage()
}