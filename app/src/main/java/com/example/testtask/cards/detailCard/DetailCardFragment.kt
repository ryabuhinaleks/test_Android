package com.example.testtask.cards.detailCard

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.example.testtask.APP_ACTIVITY
import com.example.testtask.R
import com.example.testtask.cards.updateCard.UpdateCardFragment
import com.example.testtask.model.Card
import com.example.testtask.databinding.FragmentDetailCardBinding

class DetailCardFragment : MvpAppCompatFragment(), DetailCardView {

    private lateinit var binding: FragmentDetailCardBinding
    private lateinit var idCard: String
    private lateinit var card: Card

    @InjectPresenter
    lateinit var presenter: DetailCardPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        idCard = arguments?.getString(ARG_OPTIONS)!!
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        binding = FragmentDetailCardBinding.inflate(inflater, container, false)

        presenter.getPropertyCard(idCard)

        with(binding.topbarList) {
            title.text = "#${idCard}"

            back.visibility = View.VISIBLE
            edit.visibility = View.VISIBLE

            back.setOnClickListener { APP_ACTIVITY.back() }
            edit.setOnClickListener {
               APP_ACTIVITY.launchFragment(UpdateCardFragment.newInstance(card))
            }
        }
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun showPropertyOnDisplay() {
        with(binding) {
            price.text = "${APP_ACTIVITY.getText(R.string.price)}: ${card.price}"
            place.text = "${APP_ACTIVITY.getText(R.string.place)}: ${card.place}"
            area.text = "${APP_ACTIVITY.getText(R.string.area)}: ${card.area}"

            showPropertyWithNullOnDisplay(count, card.count, R.string.countRooms)
            showPropertyWithNullOnDisplay(price2, card.price2, R.string.priceM)
            showPropertyWithNullOnDisplay(floor, card.floor, R.string.floor)

            showImage()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun showPropertyWithNullOnDisplay(field: TextView, value: Any?, id: Int) {
        when(value.toString().toIntOrNull()) {
            null -> field.visibility = View.GONE
            else -> field.text = "${APP_ACTIVITY.getText(id)}: $value"
        }
    }

    override fun showImage() {
        APP_ACTIVITY.loadImage(card.image, binding.image)
    }

    override fun setCard(cardInfo: Card) {
        card = cardInfo
        showPropertyOnDisplay()
    }

    companion object {
        @JvmStatic private val ARG_OPTIONS = "ARG_OPTIONS"

        fun newInstance(id: String): DetailCardFragment {
            val args = Bundle()
            args.putString(ARG_OPTIONS, id)
            val fragment = DetailCardFragment()
            fragment.arguments = args
            return fragment
        }
    }
}