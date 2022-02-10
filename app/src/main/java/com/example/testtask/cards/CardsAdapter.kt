package com.example.testtask.cards

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testtask.APP_ACTIVITY
import com.example.testtask.R
import com.example.testtask.model.Card
import com.example.testtask.databinding.CardBinding

interface CardsListener {
    fun cardDetail(card: Card)
}

class CardsAdapter( private val cardsListener: CardsListener) : RecyclerView.Adapter<CardsAdapter.CardsViewHolder>(), View.OnClickListener {

    var cards: List<Card> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class CardsViewHolder(
        val binding: CardBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: CardBinding = CardBinding.inflate(inflater, parent, false)

        binding.root.setOnClickListener(this)

        return CardsViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CardsViewHolder, position: Int) {
        val card = cards[position]
        with(holder.binding) {
            holder.itemView.tag = card

            price.text = card.price.toString()
            place.text = "${APP_ACTIVITY.getText(R.string.place)}: ${card.place}"
            area.text = "${APP_ACTIVITY.getText(R.string.area)}: ${card.area} кв.м."

            APP_ACTIVITY.loadImage(card.image, image)

        }
    }

    override fun getItemCount(): Int = cards.size

    override fun onClick(v: View) {
        val card = v.tag as Card
        cardsListener.cardDetail(card)
    }


}