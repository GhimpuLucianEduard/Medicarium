package com.medicorum.Presentation.Login

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProviders
import com.github.pwittchen.rxbiometric.library.RxBiometric
import com.github.pwittchen.rxbiometric.library.throwable.AuthenticationError
import com.github.pwittchen.rxbiometric.library.throwable.AuthenticationFail
import com.github.pwittchen.rxbiometric.library.throwable.AuthenticationHelp
import com.github.pwittchen.rxbiometric.library.throwable.BiometricNotSupported
import com.github.pwittchen.rxbiometric.library.validation.Preconditions
import com.github.pwittchen.rxbiometric.library.validation.RxPreconditions
import com.medicorum.Presentation.BaseFragment
import com.medicorum.R
import com.medicorum.databinding.FragmentFingerprintAuthBinding
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_fingerprint_auth.*
import org.jetbrains.anko.Android
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import java.util.concurrent.Executor


class FingerprintAuthFragment : BaseFragment(), KodeinAware {

    override val kodein by closestKodein()
    private val viewModelFactory: LoginFragmentViewModelFactory by instance()
    private lateinit var viewModel: LoginFragmentViewModel
    private lateinit var binding : FragmentFingerprintAuthBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(LoginFragmentViewModel::class.java)
        binding = FragmentFingerprintAuthBinding.inflate(inflater, container, false).apply {
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
        }
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
                        .executor(MainThreadExecutor())
                        .build()
                        .authenticate(activity!!)
            }
            .subscribeOn(AndroidSchedulers.mainThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onComplete = { fingerPrintLabel.text = getString(R.string.success_msg) },
                onError = {
                    when (it) {
                        is AuthenticationError -> {
                            fingerPrintLabel.text = getString(R.string.fingerprint_too_many_attempts_error)
                            fingerPrintLabel.setTextColor(resources.getColor(R.color.accentRed))
                        }
                        is AuthenticationFail -> {
                            fingerPrintLabel.text = getString(R.string.failed_try_again)
                            fingerPrintLabel.startAnimation(AnimationUtils.loadAnimation(context!!, R.anim.wooble))
                            authenticateUser()
                        }
                        is AuthenticationHelp -> fingerPrintLabel.text = "Auth Help??"
                        is BiometricNotSupported -> fingerPrintLabel.text = getString(R.string.biometric_not_supported)
                        else -> Log.e("ERROR", "Some other error in fingerprint auth.")
                    }
                }
            ).addTo(compositeDisposable)
    }

    inner class MainThreadExecutor : Executor {
        private val handler = Handler(Looper.getMainLooper())

        override fun execute(r: Runnable) {
            handler.post(r)
        }
    }
}
