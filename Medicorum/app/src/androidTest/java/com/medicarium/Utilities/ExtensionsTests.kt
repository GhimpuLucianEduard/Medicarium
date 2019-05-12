package com.medicarium.Utilities

import empty
import isEmailValid
import isPasswordValid
import isPhoneNumberValid
import isValidPin
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ExtensionsTests {

    @Test
    fun empty() {
        val testString = String.empty()
        assertTrue(testString == "")
    }

    @Test
    fun isEmailValid1() {
        val testEmail = "lucian@gmai.com"
        assertTrue(testEmail.isEmailValid())
    }

    @Test
    fun isEmailValid2() {
        val testEmail = "lucian@"
        assertFalse(testEmail.isEmailValid())
    }

    @Test
    fun isValidPin1() {
        val testPin = "2222"
        assertTrue(testPin.isValidPin())
    }

    @Test
    fun isValidPin2() {
        val testPin = "222"
        assertFalse(testPin.isValidPin())
    }

    @Test
    fun isPasswordValid1() {
        val testPassword = "sdasdsadasdasdsa"
        assertTrue(testPassword.isPasswordValid())
    }


    @Test
    fun isPasswordValid2() {
        val testPassword = "sda"
        assertFalse(testPassword.isPasswordValid())
    }


    @Test
    fun isPhoneNumberValid1() {
        val testNr = "0909998987"
        assertTrue(testNr.isPhoneNumberValid())
    }


    @Test
    fun isPhoneNumberValid2() {
        val testNr = "fsdfsdfasdf"
        assertFalse(testNr.isPhoneNumberValid())
    }
}