package com.medicarium.Presentation.History

import android.app.Application
import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.InstrumentationRegistry
import com.medicarium.Data.ApiServices.MedicalRecordService
import com.medicarium.Data.ApiServices.MedicalRecordServiceImpl
import com.medicarium.Data.ApiServices.RefitServices.ApiServiceFactory
import com.medicarium.Data.ApiServices.RefitServices.ConnectivityInterceptor
import com.medicarium.Data.ApiServices.RefitServices.ConnectivityInterceptorImpl
import com.medicarium.Data.Enums.MedicalCategory
import com.medicarium.Presentation.Services.ConnectivityService
import com.medicarium.Presentation.Services.ConnectivityServiceImpl
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.*


class MedicalRecordsViewModelTest {
    private lateinit var viewModel: MedicalRecordsViewModel
    private lateinit var connectivityService: ConnectivityService
    private lateinit var connectivityInterceptor: ConnectivityInterceptor
    private lateinit var apiServiceFactory: ApiServiceFactory
    private lateinit var medicalRecordService: MedicalRecordService
    private var syncObject = Object()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun doBeforeTest() {
        val app = InstrumentationRegistry.getTargetContext().applicationContext as Application
        connectivityService = ConnectivityServiceImpl()
        connectivityInterceptor = ConnectivityInterceptorImpl(connectivityService)
        apiServiceFactory = ApiServiceFactory(connectivityInterceptor)
        medicalRecordService = MedicalRecordServiceImpl(apiServiceFactory)
        viewModel = MedicalRecordsViewModel(app, medicalRecordService)
    }

    @Test
    fun addMedicalRecord() {
        val oldCount = viewModel.medicalRecords.value!!.count()
        Log.e("TEST", "Old count: $oldCount")

        if (connectivityService.hasConnection(InstrumentationRegistry.getTargetContext())) {
            Log.e("TEST", "Has Internet connection")
            viewModel.medicalRecordToAdd.value!!.timestamp = Calendar.getInstance().timeInMillis
            viewModel.medicalRecordToAdd.value!!.medicalCategory = MedicalCategory.OTOLOGY

            assertFalse(viewModel.isBusy.value!!)
            viewModel.addMedicalRecord()
            assertTrue(viewModel.isBusy.value!!)
            Thread.sleep(3000)
            assertTrue(viewModel.medicalRecords.value!!.count() > oldCount)
            assertFalse(viewModel.isBusy.value!!)
        } else {
            Log.e("TEST", "No Internet connection")
        }
    }
}