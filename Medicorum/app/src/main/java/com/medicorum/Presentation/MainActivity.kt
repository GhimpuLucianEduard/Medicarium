package com.medicorum.Presentation

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.medicorum.R
import kotlinx.android.synthetic.main.activity_main.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein

class MainActivity : AppCompatActivity(), KodeinAware {

    override val kodein by closestKodein()
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController = Navigation.findNavController(this, R.id.nav_host_fragment)

        // set bottom navigation
        bottom_nav.setupWithNavController(navController)

        val navHost = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment?
        val navController = navHost!!.navController

        val navInflater = navController.navInflater
        val graph = navInflater.inflate(R.navigation.navigation_graph)
        graph.startDestination = R.id.firstSignupFragment
        navController.graph = graph
    }

    fun setBottomBarVisibility(bool: Boolean) {
        bottom_nav.visibility = if (bool) View.VISIBLE else View.GONE
    }

    // 0 - short, 1 - long
    fun showToast(msg: String, length: Int) {
        Toast.makeText(this, msg, length).show()
    }
}
