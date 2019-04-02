package com.medicorum.Utilities

class LoggerConstants {
    companion object {
        const val ERROR_TAG = "ERROR"
        const val FAIL_TAG = "FAIL"
        const val INFO_TAG = "INFO"
        const val SUCCESS_TAS = "SUCCESS"
        const val API_REQ = "API_REQ"
    }
}

class ApiConstants {
    companion object {
        const val BASE_URL = "https://medicarium-api-server.herokuapp.com"
    }
}

class SharedPreferences {
    companion object {
        const val FILE_NAME = "medicariumSharedPreferences"
        const val SECRET_KEY = "+sW23Em{86QY20Z"
        const val USER = "user"
        const val TOKEN = "token"
        const val USER_VERIFIED = "isUserSmsVerified"
    }
}