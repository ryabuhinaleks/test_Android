package com.example.testtask.cards

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.example.testtask.model.Card

@StateStrategyType(value = AddToEndSingleStrategy::class)
interface ListCardsView : MvpView {

    fun setListCard(cards: MutableList<Card>)
}