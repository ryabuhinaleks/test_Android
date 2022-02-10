package com.example.testtask.cards

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.example.testtask.R
import com.example.testtask.databinding.FragmentListCardsBinding
import com.example.testtask.model.Card

class ListCardsFragment : MvpAppCompatFragment(), ListCardsView {

    private lateinit var binding: FragmentListCardsBinding
    private lateinit var cardAdapter: CardsAdapter

    @InjectPresenter
    lateinit var presenter: ListCardsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        cardAdapter = presenter.createAdapter()

        presenter.getCards()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentListCardsBinding.inflate(inflater, container, false)

        val layoutManager = LinearLayoutManager(requireContext())
        binding.list.layoutManager = layoutManager
        binding.list.adapter = cardAdapter

        with(binding.topbarList) {
            done.visibility = View.INVISIBLE
            exit.visibility = View.VISIBLE
            add.visibility = View.VISIBLE
            title.text = getString(R.string.listCards)

            exit.setOnClickListener {
                presenter.onExit()
            }

            add.setOnClickListener {
                presenter.onAddCard()
            }
        }
        return binding.root
    }

    override fun setListCard(cards: MutableList<Card>) {
        cardAdapter.cards = cards
    }

}