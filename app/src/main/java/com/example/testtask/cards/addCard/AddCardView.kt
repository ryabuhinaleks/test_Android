package com.example.testtask.cards.addCard

import android.net.Uri
import android.widget.ImageView
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

@StateStrategyType(value = AddToEndSingleStrategy::class)
interface AddCardView : MvpView {

    fun getImage()

    fun setImage(data: Uri?)
}