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
import com.example.testtask.model.Card
import com.example.testtask.databinding.FragmentUpdateCardBinding

class UpdateCardFragment : MvpAppCompatFragment(), UpdateCardView {

    private lateinit var binding: FragmentUpdateCardBinding
    private lateinit var card: Card

    @InjectPresenter
    lateinit var presenter: UpdateCardPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        card = arguments?.getParcelable(ARG_OPTIONS)!!
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentUpdateCardBinding.inflate(inflater, container, false)

        updateUI()

        binding.uploadFile.setOnClickListener {
            getImage()
        }

        with(binding.topbarList) {
            title.text = "#${card.id}"

            back.visibility = View.VISIBLE
            done.visibility = View.VISIBLE

            back.setOnClickListener { APP_ACTIVITY.back() }

            done.setOnClickListener {
                updateDataCard()
                presenter.updateCard(card)
                card.image = presenter.updateImage()
                APP_ACTIVITY.back()
            }
        }
        return binding.root
    }

    override fun updateUI() {
        with(binding) {
            price.setText(card.price.toString())
            place.setText(card.place)
            area.setText(card.area.toString())
            count.setText(card.count?.toString())
            price2.setText(card.price2?.toString())
            floor.setText(card.floor?.toString())
        }
        showImage()
    }

    override fun showImage() {
        APP_ACTIVITY.loadImage(card.image, binding.image)
    }

    override fun updateDataCard() {
         with(binding) {
             card.price = price.text.toString().toLong()
             card.place = place.text.toString()
             card.area = area.text.toString().toInt()
             card.count = count.text.toString().toIntOrNull()
             card.price2 = price2.text.toString().toIntOrNull()
             card.floor = floor.text.toString().toIntOrNull()
        }
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
        APP_ACTIVITY.loadImage(data!!.toString(),binding.image)
    }

    companion object {
        @JvmStatic private val ARG_OPTIONS = "ARG_OPTIONS"

        fun newInstance(card: Card): UpdateCardFragment {
            val args = Bundle()
            args.putParcelable(ARG_OPTIONS, card)
            val fragment = UpdateCardFragment()
            fragment.arguments = args
            return fragment
        }
    }
}