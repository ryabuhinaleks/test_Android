package com.example.testtask.auth

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@InjectViewState
class AuthPresenter : MvpPresenter<AuthView>() {

    private var auth: FirebaseAuth = Firebase.auth

    fun signIn(email: String, password: String){
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener {
            viewState.onSignIn(it.isSuccessful)
        }
    }
}