package com.medicarium

import android.app.Application
import android.content.Context
import com.jakewharton.threetenabp.AndroidThreeTen
import com.medicarium.Data.ApiServices.AuthService
import com.medicarium.Data.ApiServices.AuthServiceImpl
import com.medicarium.Data.ApiServices.RefitServices.ApiServiceFactory
import com.medicarium.Data.ApiServices.RefitServices.ConnectivityInterceptor
import com.medicarium.Data.ApiServices.RefitServices.ConnectivityInterceptorImpl
import com.medicarium.Presentation.Login.LoginViewModelFactory
import com.medicarium.Presentation.Login.PinAuthViewModelFactory
import com.medicarium.Presentation.Pin.SetupPinViewModelFactory
import com.medicarium.Presentation.Services.*
import com.medicarium.Presentation.Signup.SignupViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class MedicorumApp : Application(), KodeinAware {

    override val kodein = Kodein.lazy {
        import(androidXModule(this@MedicorumApp))

        // ui/device related services
        bind<ToastService>() with singleton { ToastServiceImpl() }
        bind<VibrationService>() with singleton { VibrationServiceImpl() }
        bind<ConnectivityService>() with singleton { ConnectivityServiceImpl() }
        bind<DialogService>() with singleton { DialogServiceImpl() }

        // api stuff
        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }
        bind() from singleton { ApiServiceFactory(instance()) }

        // api services
        bind<AuthService>() with singleton { AuthServiceImpl(instance()) }

        // view models
        bind() from provider { LoginViewModelFactory(instance(), instance()) }
        bind() from provider { SignupViewModelFactory(instance(), instance(), instance()) }
        bind() from provider { SetupPinViewModelFactory(instance()) }
        bind() from provider { PinAuthViewModelFactory(instance()) }
    }

    init {
        instance = this
    }

    companion object {
        private var instance: MedicorumApp? = null

        fun applicationContext(): Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        val context: Context = applicationContext()
        AndroidThreeTen.init(this)
    }
}