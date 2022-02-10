package com.example.testtask

import android.widget.EditText
import android.widget.ImageView
import androidx.fragment.app.Fragment

lateinit var APP_ACTIVITY: MainActivity

interface HelperView {

    fun back()

    fun launchFragment(fragment: Fragment)

    fun signOut()

    fun isAuthUser() : Boolean

    fun hideKeyboard()

    fun getImage()

    fun showEmptyField(vararg field: EditText) : Boolean

    fun loadImage(url: String, container: ImageView)

    fun notification(message: String)

}
