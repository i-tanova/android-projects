package com.example.firstfirestore

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.example.BackPressedListener
import com.example.firstfirestore.data.DataManager
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.AuthUI.IdpConfig.EmailBuilder
import com.firebase.ui.auth.AuthUI.IdpConfig.GoogleBuilder
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private val RC_SIGN_IN: Int = 123
    val dataManager = DataManager()
    var products: Fragment? = null
    var todayFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        products = ProductsFragment()
        todayFragment = TodayFragment()

        if (dataManager.isAuthenticated()) {
            setupFragment(products)
        }

        bottom_navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.page_1 -> {
                    setupFragment(products)
                }
                R.id.page_2 -> {
                    setupFragment(todayFragment)
                }
            }
            true
        }
    }

    override fun onResume() {
        super.onResume()
        if (!dataManager.isAuthenticated()) {
            goToLogin()
        } else {
            setupFragment(products)
        }
    }

    private fun setupFragment(fragment: Fragment?) {
        fragment ?: return
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.main_container, fragment)
            commit()
        }
    }

    private fun goToLogin() {
        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setIsSmartLockEnabled(false) // automatic log in
                .setAvailableProviders(
                    Arrays.asList(
                        GoogleBuilder().build(),
                        EmailBuilder().build()
                    )
                )
                .build(),
            RC_SIGN_IN
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            // Login flow
            if (resultCode == RESULT_OK) {
                //User signed in
            } else {
                //Signed in cancel
                finish();
            }
        }
    }

    override fun onBackPressed() {
      tellFragments()
    }

    private fun tellFragments() {
        val fragments = supportFragmentManager.fragments
        for (f in fragments) {
            if (f != null && f is BackPressedListener) (f as BackPressedListener).onBackPressed()
        }
    }

}