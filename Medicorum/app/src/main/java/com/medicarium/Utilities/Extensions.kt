import android.annotation.SuppressLint
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.widget.EditText
import com.google.android.material.textfield.TextInputLayout

@SuppressLint("SimpleDateFormat")
fun String.Companion.timeStampToDateString(date: Long): String {
    val sdf = java.text.SimpleDateFormat("HH:mm MM-dd")
    val out = java.util.Date(date)
    return sdf.format(out)
}

fun String.Companion.empty(): String {
    return ""
}

fun String.isEmailValid(): Boolean {
    return this.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

fun String.isValidPin(): Boolean {
    return this.length == 4
}

fun String.isPasswordValid(): Boolean {
    return this.isNotEmpty() && this.length >= 8
}

fun String.isPhoneNumberValid(): Boolean {
    return this.isNotEmpty() && Patterns.PHONE.matcher(this).matches()
}

fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object: TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            afterTextChanged.invoke(s.toString())
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
    })
}

fun TextInputLayout.validate(editText: EditText, validator: (String) -> Boolean, message: String) {
    editText.afterTextChanged {
        this.error = if (validator(it)) null else message
    }
}

