package com.medicarium.Presentation

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.andreacioccarelli.cryptoprefs.CryptoPrefs
import com.cloudinary.android.MediaManager
import com.medicarium.Presentation.Services.ConnectivityService
import com.medicarium.R
import com.medicarium.Utilities.Fingerprint.FingerprintUtility
import com.medicarium.Utilities.SharedPreferences
import com.medicarium.Utilities.SharedPreferences.Companion.PIN
import com.medicarium.Utilities.SharedPreferences.Companion.TOKEN
import com.novoda.merlin.Merlin
import empty
import kotlinx.android.synthetic.main.activity_main.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance

class MainActivity : AppCompatActivity(), KodeinAware {

    override val kodein by closestKodein()
    private lateinit var navController: NavController
    private lateinit var merlin: Merlin
    var shouldDisplayNoConnectionOverlay: Boolean = false
    val connectivityService: ConnectivityService by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController = Navigation.findNavController(this, R.id.nav_host_fragment)

        // set bottom navigation
        bottom_nav.setupWithNavController(navController)

        val navHost = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment?
        navController = navHost!!.navController

        val navInflater = navController.navInflater
        val graph = navInflater.inflate(R.navigation.navigation_graph)


        val preferences = CryptoPrefs(this, SharedPreferences.FILE_NAME, SharedPreferences.SECRET_KEY)
        graph.startDestination = R.id.fingerPrintFragment

        if (preferences.get(TOKEN, String.empty()) == String.empty()) {
            graph.startDestination = R.id.loginFragment
        } else {
            if (FingerprintUtility.hasFingerprintSupport(this)) {
                graph.startDestination = R.id.fingerPrintFragment
            } else {

                var hasPin = preferences.get(PIN, String.empty()) != String.empty()

                if (hasPin) {
                    graph.startDestination = R.id.pinAuthFragment
                } else {
                    graph.startDestination = R.id.setupPinFragment
                }
            }
        }

        // TODO remove
        //graph.startDestination = R.id.medicalRecordDetailsFragment

        navController.graph = graph

        merlin = Merlin.Builder()
            .withConnectableCallbacks()
            .withDisconnectableCallbacks()
            .build(this)

        merlin.registerConnectable {
            runOnUiThread {
                noConnectionOverlay.visibility = View.GONE
            }
        }

        merlin.registerDisconnectable {
            runOnUiThread {
                if (shouldDisplayNoConnectionOverlay) {
                    noConnectionOverlay.visibility = View.VISIBLE
                }
            }
        }
    }

    fun refreshConnectivity(shoudlUseOverlay: Boolean) {
        if (shoudlUseOverlay && !connectivityService.hasConnection(this)) {
                noConnectionOverlay.visibility = View.VISIBLE
        } else {
            noConnectionOverlay.visibility = View.GONE
        }
    }

    fun setBottomBarVisibility(bool: Boolean) {
        bottom_nav.visibility = if (bool) View.VISIBLE else View.GONE
    }

    // 0 - short, 1 - long
    fun showToast(msg: String, length: Int) {
        Toast.makeText(this, msg, length).show()
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, null)
    }

    override fun onResume() {
        super.onResume()
        merlin.bind()
    }

    override fun onPause() {
        merlin.unbind()
        super.onPause()
    }
}
