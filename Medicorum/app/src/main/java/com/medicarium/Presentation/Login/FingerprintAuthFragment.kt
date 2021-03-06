package com.medicarium.Presentation.Login

import android.annotation.TargetApi
import android.content.Context
import android.content.Context.FINGERPRINT_SERVICE
import android.hardware.fingerprint.FingerprintManager
import android.os.Build
import android.os.Bundle
import android.os.CancellationSignal
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.medicarium.Presentation.BaseFragment
import com.medicarium.Presentation.Services.VibrationService
import com.medicarium.R
import com.medicarium.Utilities.Fingerprint.FingerprintUtility
import com.medicarium.Utilities.LoggerConstants.Companion.ERROR_TAG
import com.medicarium.Utilities.LoggerConstants.Companion.FAIL_TAG
import com.medicarium.Utilities.LoggerConstants.Companion.INFO_TAG
import com.medicarium.databinding.FragmentFingerprintAuthBinding
import kotlinx.android.synthetic.main.fragment_fingerprint_auth.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance


class FingerprintAuthFragment : BaseFragment(), KodeinAware {

    override val kodein by closestKodein()
    private lateinit var binding : FragmentFingerprintAuthBinding
    private lateinit var fingerPrintHandler: FingerprintHandler
    private lateinit var cancellationSignal: CancellationSignal

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setBottomBarVisibility(false)
        return inflater.inflate(R.layout.fragment_fingerprint_auth, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (FingerprintUtility.hasFingerprintSupport(context!!)) {
            FingerprintUtility.generateEncryptionKey()

            if (FingerprintUtility.initCipher()) {

                fingerPrintHandler = FingerprintHandler(context!!)
                val fingerPrintManager = context!!.getSystemService(FINGERPRINT_SERVICE) as? FingerprintManager
                cancellationSignal = CancellationSignal()
                fingerPrintManager!!.authenticate(null, cancellationSignal, 0, fingerPrintHandler, null)

            }
        }

        pinLabel.setOnClickListener {
            cancellationSignal?.cancel()
            Navigation.findNavController(activity!!, R.id.nav_host_fragment)
                .navigate(FingerprintAuthFragmentDirections.actionFingerPrintFragmentToPinAuthFragment())
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    inner class FingerprintHandler(context: Context) : FingerprintManager.AuthenticationCallback() {

        override fun onAuthenticationError(errorCode: Int, errString: CharSequence?) {
            // errorCode = 5 => cancellation signal (switched to pin auth)
            if (errorCode != 5) {
                val vibrator: VibrationService by instance()
                vibrator.vibrate(context!!, 300)
                fingerPrintLabel?.text = getString(R.string.fingerprint_too_many_attempts_error)
                fingerPrintLabel?.setTextColor(resources.getColor(R.color.accentRed))
                Log.e(ERROR_TAG, "AuthenticationError: ${errString.toString()}")
            }
        }

        override fun onAuthenticationSucceeded(result: FingerprintManager.AuthenticationResult?) {
            super.onAuthenticationSucceeded(result)
            fingerPrintLabel.text = getString(R.string.success_msg)
            Navigation.findNavController(activity!!, R.id.nav_host_fragment)
                .navigate(FingerprintAuthFragmentDirections.actionFingerPrintFragmentToGenericInfoFragment())
        }

        override fun onAuthenticationHelp(helpCode: Int, helpString: CharSequence?) {
            super.onAuthenticationHelp(helpCode, helpString)
            Log.e(INFO_TAG, "AuthenticationHelp")
        }

        override fun onAuthenticationFailed() {
            super.onAuthenticationFailed()
            fingerPrintLabel.text = getString(R.string.failed_try_again)
            fingerPrintLabel.startAnimation(AnimationUtils.loadAnimation(context!!, R.anim.shake))
            Log.e(FAIL_TAG, "AuthenticationFailed")
        }
    }
}
