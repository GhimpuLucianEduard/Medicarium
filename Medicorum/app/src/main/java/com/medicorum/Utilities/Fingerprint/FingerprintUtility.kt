package com.medicorum.Utilities.Fingerprint

import android.Manifest
import android.annotation.TargetApi
import android.app.KeyguardManager
import android.content.Context
import android.content.Context.FINGERPRINT_SERVICE
import android.content.Context.KEYGUARD_SERVICE
import android.content.pm.PackageManager
import android.hardware.fingerprint.FingerprintManager
import android.os.Build
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Log
import androidx.core.app.ActivityCompat
import java.lang.Exception
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey

// N.B. using FingerprintManager instead of BiometricDialog to create own UI
@TargetApi(Build.VERSION_CODES.M)
class FingerprintUtility {


    companion object {

        private const val ANDROID_KEY_STORE = "AndroidKeyStore"
        private const val KEY_NAME = "fingerPrintKey"
        private lateinit var keyStore: KeyStore

        fun hasFingerprintSupport(context: Context): Boolean {

            // check if droid.M
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val keyguardManager = context.getSystemService(KEYGUARD_SERVICE) as? KeyguardManager
                val fingerprintManager = context.getSystemService(FINGERPRINT_SERVICE) as? FingerprintManager

                // check hardware
                if (!fingerprintManager!!.isHardwareDetected) {
                    return false
                }

                // check permission
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
                    return false
                }

                // check if at least 1 fingerprint is enrolled
                if (!fingerprintManager.hasEnrolledFingerprints()) {
                    return false
                }

                if (!keyguardManager!!.isKeyguardSecure) {
                    return false
                }

                return true
            }

            return false
        }

        fun generateEncryptionKey() {

            keyStore = KeyStore.getInstance(ANDROID_KEY_STORE)
            val keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES,
                ANDROID_KEY_STORE
            );

            // init empty keyStore
            keyStore.load(null)


            keyGenerator.init(KeyGenParameterSpec.Builder(KEY_NAME, KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setUserAuthenticationRequired(true)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
                    .build())

            keyGenerator.generateKey()
        }

        fun initCipher(): Boolean {

            try {
                val cipher = Cipher.getInstance("${KeyProperties.KEY_ALGORITHM_AES}/${KeyProperties.BLOCK_MODE_CBC}/${KeyProperties.ENCRYPTION_PADDING_PKCS7}")
                keyStore.load(null);
                val key = keyStore.getKey(KEY_NAME, null) as? SecretKey
                cipher.init(Cipher.ENCRYPT_MODE, key)
            } catch (e: Exception) {
                Log.e("CIPHER_ERROR", e.message)
                return false
            }
            return true
        }
    }
}