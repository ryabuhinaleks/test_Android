package com.example.testtask.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.example.testtask.databinding.FragmentAuthBinding
import com.example.testtask.*
import com.example.testtask.cards.ListCardsFragment

class AuthFragment : MvpAppCompatFragment(), AuthView {

    private lateinit var binding: FragmentAuthBinding

    @InjectPresenter
    lateinit var presenter: AuthPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentAuthBinding.inflate(inflater, container, false)

        with(binding) {
            btnAuth.setOnClickListener {
                if (APP_ACTIVITY.showEmptyField(login, password)) {
                    presenter.signIn(login.text.toString(), password.text.toString())
                }
            }
        }
        return binding.root
    }

    override fun onSignIn(result: Boolean) {
        when(result) {
            true ->  APP_ACTIVITY.launchFragment(ListCardsFragment())
            false -> APP_ACTIVITY.notification(getString(R.string.user_is_not_registered))
        }
    }
}