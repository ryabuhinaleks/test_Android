package com.example.testtask.cards

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.example.testtask.model.Card

@StateStrategyType(value = OneExecutionStateStrategy::class)
interface ListCardsView : MvpView {

    fun setListCard(card: Card)
}