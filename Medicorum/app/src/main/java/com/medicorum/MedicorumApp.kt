package com.medicorum

import android.app.Application
import android.content.Context
import com.jakewharton.threetenabp.AndroidThreeTen
import com.medicorum.Data.ApiServices.AuthService
import com.medicorum.Data.ApiServices.AuthServiceImpl
import com.medicorum.Data.ApiServices.RefitServices.ApiServiceFactory
import com.medicorum.Data.ApiServices.RefitServices.ConnectivityInterceptor
import com.medicorum.Data.ApiServices.RefitServices.ConnectivityInterceptorImpl
import com.medicorum.Presentation.Login.LoginFragmentViewModelFactory
import com.medicorum.Presentation.Services.*
import com.medicorum.Presentation.Signup.SignUpViewModelFactory
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

        // api stuff
        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }
        bind() from singleton { ApiServiceFactory(instance()) }

        // api services
        bind<AuthService>() with singleton { AuthServiceImpl(instance()) }

        // view models
        bind() from provider { LoginFragmentViewModelFactory() }
        bind() from provider { SignUpViewModelFactory(instance(), instance(), instance()) }
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