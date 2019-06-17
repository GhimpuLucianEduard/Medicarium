package com.medicarium.Presentation.EmergencyMode

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.andreacioccarelli.cryptoprefs.CryptoPrefs
import com.medicarium.Data.LocalDb.UserRepository
import com.medicarium.Presentation.BaseFragment
import com.medicarium.R
import com.medicarium.Utilities.SharedPreferences
import kotlinx.android.synthetic.main.emergency_mode_fragment.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class EmergencyMode : BaseFragment(), KodeinAware {

    override val kodein by closestKodein()
    val userRepository: UserRepository by instance()
    private lateinit var adapter: EmergencyModeListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.emergency_mode_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buildAdapter()

        closeTextView.setOnClickListener {
            Navigation.findNavController(activity!!, R.id.nav_host_fragment)
                .navigateUp()
        }
    }

    private fun buildAdapter() {

        val user = userRepository.getUserData()

        val listOfEntries = ArrayList<EmergencyModeEntry>()

        // TODO: refactor this, not very proud of this
        if (user.isFirstNameVisible) listOfEntries.add(EmergencyModeEntry("First Name:", user.firstName))
        if (user.isLastNameVisible) listOfEntries.add(EmergencyModeEntry("Last Name:", user.lastName))
        if (user.isPhoneNumberVisible) listOfEntries.add(EmergencyModeEntry("Phone Number:", user.phoneNumber))
        if (user.isBloodTypeVisible) listOfEntries.add(EmergencyModeEntry("Blood Type:", user.bloodType.toString()))
        if (user.isGenderVisible) listOfEntries.add(EmergencyModeEntry("Gender:", user.gender.toString()))
        if (user.isWeightVisible) listOfEntries.add(EmergencyModeEntry("Weight:", user.weight +" (kg)"))
        if (user.isHeightVisible) listOfEntries.add(EmergencyModeEntry("Height:", user.height + " (cm)"))
        if (user.isEmergencyContactNameVisible) listOfEntries.add(EmergencyModeEntry("Contact Name:", user.emergencyContactName))
        if (user.isEmergencyContactPhoneNumberVisible) listOfEntries.add(EmergencyModeEntry("Contact Phone Number:", user.emergencyContactPhoneNumber))

        adapter = EmergencyModeListAdapter(listOfEntries)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter
    }

}
