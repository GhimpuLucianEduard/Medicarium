package com.medicarium

import android.app.Application
import android.content.Context
import com.cloudinary.android.MediaManager
import com.jakewharton.threetenabp.AndroidThreeTen
import com.medicarium.Data.ApiServices.*
import com.medicarium.Data.ApiServices.RefitServices.ApiServiceFactory
import com.medicarium.Data.ApiServices.RefitServices.ConnectivityInterceptor
import com.medicarium.Data.ApiServices.RefitServices.ConnectivityInterceptorImpl
import com.medicarium.Data.LocalDb.AppDatabase
import com.medicarium.Data.LocalDb.UserRepository
import com.medicarium.Data.LocalDb.UserRepositoryImpl
import com.medicarium.Presentation.General.GenericInfoViewModelFactory
import com.medicarium.Presentation.History.MedicalRecordDetailsViewModelFactory
import com.medicarium.Presentation.History.MedicalRecordsViewModelFactory
import com.medicarium.Presentation.Login.LoginViewModelFactory
import com.medicarium.Presentation.Login.PinAuthViewModelFactory
import com.medicarium.Presentation.Pin.SetupPinViewModelFactory
import com.medicarium.Presentation.Services.*
import com.medicarium.Presentation.Settings.SettingsViewModelFactory
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
        bind<MedicalRecordService>() with singleton { MedicalRecordServiceImpl(instance()) }
        bind<UserPreferencesService>() with singleton { UserPreferencesServiceImpl(instance()) }

        // db
        bind() from singleton { AppDatabase(instance()) }
        bind() from singleton { instance<AppDatabase>().userDao() }

        // repo
        bind<UserRepository>() with singleton { UserRepositoryImpl(instance()) }

        // view models
        bind() from provider { LoginViewModelFactory(instance(), instance(), instance()) }
        bind() from provider { SignupViewModelFactory(instance(), instance(), instance(), instance()) }
        bind() from provider { SetupPinViewModelFactory(instance()) }
        bind() from provider { PinAuthViewModelFactory(instance()) }
        bind() from singleton { MedicalRecordsViewModelFactory(instance(), instance()) }
        bind() from singleton { MedicalRecordDetailsViewModelFactory(instance(), instance(), instance()) }
        bind() from singleton { GenericInfoViewModelFactory(instance(), instance(), instance(), instance()) }
        bind() from singleton { SettingsViewModelFactory(instance(), instance()) }
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
        MediaManager.init(this)
        val context: Context = applicationContext()
        AndroidThreeTen.init(this)
    }
}