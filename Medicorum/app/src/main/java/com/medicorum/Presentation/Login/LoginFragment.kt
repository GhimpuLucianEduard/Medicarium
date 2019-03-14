package com.medicorum.Presentation.Login

import android.Manifest
import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.hardware.biometrics.BiometricPrompt
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.os.CancellationSignal
import androidx.lifecycle.ViewModelProviders
import com.github.pwittchen.rxbiometric.library.RxBiometric
import com.github.pwittchen.rxbiometric.library.throwable.AuthenticationError
import com.github.pwittchen.rxbiometric.library.throwable.AuthenticationFail
import com.github.pwittchen.rxbiometric.library.throwable.AuthenticationHelp
import com.github.pwittchen.rxbiometric.library.throwable.BiometricNotSupported
import com.github.pwittchen.rxbiometric.library.validation.Preconditions
import com.github.pwittchen.rxbiometric.library.validation.RxPreconditions
import com.medicorum.Presentation.BaseFragment
import com.medicorum.databinding.FragmentLoginBinding
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class LoginFragment : BaseFragment(), KodeinAware {

    override val kodein by closestKodein()
    private val viewModelFactory: LoginFragmentViewModelFactory by instance()
    private lateinit var viewModel: LoginFragmentViewModel
    private lateinit var binding : FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(LoginFragmentViewModel::class.java)
        binding = FragmentLoginBinding.inflate(inflater, container, false).apply {
            loginViewModel = viewModel
        }

//        binding.loginButton.setOnClickListener {
//            Navigation.findNavController(activity!!, R.id.nav_host_fragment).navigate(R.id.action_loginFragment_to_genericInfoFragment)
//        }
        setBottomBarVisibility(false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (Preconditions.hasBiometricSupport(context!!)) {
            authenticateUser()
        } else {
            showToast("no fingerprint support")
        }
    }

    private fun checkBiometricSupport(): Boolean {

        if (ActivityCompat.checkSelfPermission(context!!, Manifest.permission.USE_BIOMETRIC) != PackageManager.PERMISSION_GRANTED) {

            showToast("Fingerprint authentication permission not enabled")
            return false
        }

        if (ActivityCompat.checkSelfPermission(context!!, Manifest.permission.USE_BIOMETRIC) !=
            PackageManager.PERMISSION_GRANTED) {

            showToast("Fingerprint authentication permission not enabled");
            return false
        }

        if (activity!!.packageManager.hasSystemFeature(PackageManager.FEATURE_FINGERPRINT))
        {
            return true
        }

        return true
    }

    @SuppressLint("NewApi")
    public fun authenticateUser() {
        RxPreconditions
            .hasBiometricSupport(context!!)
            .flatMapCompletable {
                if (!it) Completable.error(BiometricNotSupported())
                else
                    RxBiometric
                        .title("title")
                        .description("description")
                        .negativeButtonText("cancel")
                        .negativeButtonListener(DialogInterface.OnClickListener { _, _ ->
                            showToast("cancel")
                        })
                        .executor(ActivityCompat.getMainExecutor(context!!))
                        .build()
                        .authenticate(activity!!)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onComplete = { showToast("authenticated!") },
                onError = {
                    when (it) {
                        is AuthenticationError -> showToast("error")
                        is AuthenticationFail -> showToast("fail")
                        is AuthenticationHelp -> showToast("help")
                        is BiometricNotSupported -> showToast("biometric not supported")
                        else -> showToast("other error")
                    }
                }
            ).addTo(compositeDisposable)
    }
}
