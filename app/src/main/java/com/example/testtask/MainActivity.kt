package com.example.testtask

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.testtask.auth.AuthFragment
import com.example.testtask.cards.ListCardsFragment
import com.example.testtask.databinding.ActivityMainBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity(), HelperView {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        APP_ACTIVITY = this

        binding = ActivityMainBinding.inflate(layoutInflater).also{ setContentView(it.root) }

        val currentFragment = when(isAuthUser()) {
            true -> ListCardsFragment()
            false -> AuthFragment()
        }

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragmentContainer, currentFragment)
                .commit()
        }
    }

    override fun back() {
        onBackPressed()
    }

    override fun launchFragment(fragment: Fragment) {
        hideKeyboard()

        supportFragmentManager
            .beginTransaction()
            .addToBackStack(null)
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }

    override fun hideKeyboard() {
        val view: View? = this.currentFocus
        if (view != null) {
            val imm: InputMethodManager =
                getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && data != null && data.data != null) {

            if (resultCode == RESULT_OK) {
                Toast.makeText(this, data.data.toString(), Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun getImage() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(intent, 1)
    }

    override fun showEmptyField(vararg field: EditText): Boolean {
        var result = true;
        field.map {
            if (it.text.toString().isEmpty()) {
                it.error = APP_ACTIVITY.getString(R.string.emptyField)
                result = false
            }
        }
        return result
    }

    override fun signOut() = Firebase.auth.signOut()

    override fun isAuthUser() = Firebase.auth.currentUser != null

    override fun loadImage(url: String, container: ImageView) {
        Glide.with(APP_ACTIVITY)
            .load(url)
            .error(R.drawable.ic_broken_image)
            .placeholder(R.drawable.ic_default)
            .into(container)
    }

    override fun notification(message: String) {
        Toast.makeText(APP_ACTIVITY, message, Toast.LENGTH_SHORT).show()
    }
}