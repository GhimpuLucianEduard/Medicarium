package com.medicorum.Utilities

import android.content.Context
import android.hardware.fingerprint.FingerprintManager
import android.os.Build
import androidx.core.hardware.fingerprint.FingerprintManagerCompat

class BiometricUtilities {
    companion object {

        fun isBiometricPromptEnabled(): Boolean = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P)

        // fingerprint auth is only supported from droid 6.0
        fun isSdkVersionSupported(): Boolean = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)

    }
}