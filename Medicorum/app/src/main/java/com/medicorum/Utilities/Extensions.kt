import android.annotation.SuppressLint

@SuppressLint("SimpleDateFormat")
fun String.Companion.timeStampToDateString(date: Long): String {
    val sdf = java.text.SimpleDateFormat("HH:mm MM-dd")
    val out = java.util.Date(date)
    return sdf.format(out)
}

fun String.Companion.empty(): String {
    return ""
}

fun String.Companion.isEmailValid(email: String): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}
