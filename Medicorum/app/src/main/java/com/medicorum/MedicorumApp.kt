package com.medicorum

import android.app.Application
import android.content.Context
import com.jakewharton.threetenabp.AndroidThreeTen
import com.medicorum.Presentation.Login.LoginFragmentViewModelFactory
import com.medicorum.Presentation.Services.VibrationService
import com.medicorum.Presentation.Services.VibrationServiceImpl
import com.medicorum.Presentation.Signup.SignUpViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class MedicorumApp : Application(), KodeinAware {

    override val kodein = Kodein.lazy {
        import(androidXModule(this@MedicorumApp))

        bind() from provider { LoginFragmentViewModelFactory() }
        bind() from provider { SignUpViewModelFactory() }
        bind<VibrationService>() with singleton { VibrationServiceImpl() }
    }

    init {
        instance = this
    }

    companion object {
        private var instance: MedicorumApp? = null

        fun applicationContext() : Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        val context: Context = applicationContext()
        AndroidThreeTen.init(this)
    }
}