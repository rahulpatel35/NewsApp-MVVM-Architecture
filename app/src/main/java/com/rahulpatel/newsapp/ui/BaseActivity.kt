package com.rahulpatel.newsapp.ui

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

/*Every activity just extends BaseActivity with its own binding.
setupUI() is where you put activity-specific logic.
Edge-to-edge + toolbar setup is handled consistently everywhere.*/

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // AndroidX API (works well on 13/14/15)
    }


    protected fun applyEdgeToEdge(view: View) {
        ViewCompat.setOnApplyWindowInsetsListener(view) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    /**
     * Apply edge-to-edge padding to the root view.
     * Also adjusts toolbar for status bar height.
     */
    protected fun applyEdgeToEdge(root: View, toolbar: Toolbar? = null) {
        ViewCompat.setOnApplyWindowInsetsListener(root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())

            // Apply padding to root layout (for nav bar, gesture insets, etc.)
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)

            // Push toolbar down below the status bar
            toolbar?.setPadding(
                toolbar.paddingLeft,
                systemBars.top,
                toolbar.paddingRight,
                toolbar.paddingBottom
            )

            insets
        }
    }

    /**
     * Helper to set up a toolbar with back button
     */
    protected fun setupToolbar(toolbar: Toolbar, showBack: Boolean = true) {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(showBack)
        supportActionBar?.setDisplayShowTitleEnabled(true)

        if (showBack) {
            toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }
        }
    }
}
