package com.example.testtask.cards.updateCard

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.example.testtask.APP_ACTIVITY
import com.example.testtask.R
import com.example.testtask.cards.addCard.AddCardPresenter
import com.example.testtask.cards.addCard.AddCardView
import com.example.testtask.databinding.FragmentUpdateCardBinding
import com.example.testtask.model.Card

class AddCardFragment : MvpAppCompatFragment(), AddCardView {

    private lateinit var binding: FragmentUpdateCardBinding

    @InjectPresenter
    lateinit var presenter: AddCardPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentUpdateCardBinding.inflate(inflater, container, false)

        binding.uploadFile.setOnClickListener {
            getImage()
        }

        with(binding.topbarList) {
            title.text = getString(R.string.new_card)

            buttonStart.setOnClickListener {
                APP_ACTIVITY.back()
            }

            buttonEnd.setOnClickListener {
                with(binding) {
                    when (APP_ACTIVITY.showEmptyField(price, area, place)) {
                        true -> {
                            Card(
                                price = price.text.toString().toLong(),
                                area = area.text.toString().toInt(),
                                place = place.text.toString(),

                                count = count.text.toString().toIntOrNull(),
                                price2 = price2.text.toString().toIntOrNull(),
                                floor = floor.text.toString().toIntOrNull()
                            ).also { card ->
                                presenter.createCard(card = card)
                                APP_ACTIVITY.back()
                            }
                        }
                    }
                }
            }
        }
        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && data != null && data.data != null) {

            if (resultCode == AppCompatActivity.RESULT_OK) {
                presenter.setNewImage(data = data.data)
            }
        }
    }

    override fun getImage() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(intent, 1)
    }

    override fun setImage(data: Uri?) {
        binding.image.setImageURI(data)
    }
}