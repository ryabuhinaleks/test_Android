package com.example.testtask.cards.detailCard

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.example.testtask.model.Card

@StateStrategyType(value = AddToEndSingleStrategy::class)
interface DetailCardView : MvpView {

    fun showPropertyOnDisplay()

    fun showImage()

    fun setCard(cardInfo: Card)
}