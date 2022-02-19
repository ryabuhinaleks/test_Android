package com.example.testtask.auth

import android.annotation.SuppressLint
import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import durdinapps.rxfirebase2.RxFirebaseAuth

@InjectViewState
class AuthPresenter : MvpPresenter<AuthView>() {

    private var auth: FirebaseAuth = Firebase.auth

    @SuppressLint("CheckResult")
    fun signIn(email: String, password: String){

        RxFirebaseAuth.signInWithEmailAndPassword(auth, email, password)
            .map {
                authResult -> authResult != null
            }
            .subscribe(
                {
                    response -> viewState.onSignIn(response)
                },
                {
                    error -> Log.e("Auth_Error", error.toString())
                    viewState.onSignIn(false)
                },
            )
    }
}