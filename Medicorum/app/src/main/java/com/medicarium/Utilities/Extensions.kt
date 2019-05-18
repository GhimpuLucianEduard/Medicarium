import android.annotation.SuppressLint
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.MotionEvent
import android.widget.EditText
import android.widget.ImageButton
import androidx.lifecycle.MutableLiveData
import com.google.android.material.textfield.TextInputLayout
import com.medicarium.Data.Models.MedicalRecord
import com.medicarium.Data.Models.MedicalRecordEntry
import java.util.*
import java.util.function.Predicate
import java.util.stream.Collectors
import kotlin.collections.ArrayList

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

fun ImageButton.addScaleAnimation() {
    this.setOnTouchListener { v, event ->
        if (event.action == MotionEvent.ACTION_DOWN) {
            v.animate().scaleX(0.9f).scaleY(0.9f).duration = 100
            true
        } else if (event.action == MotionEvent.ACTION_UP) {
            v.animate().scaleX(1f).scaleY(1f).duration = 100
            true
        }
        false
    }
}

fun <T> MutableLiveData<List<T>>.add(item: T) {
    val updatedItems = this.value as ArrayList
    updatedItems.add(item)
    this.value = updatedItems
}

fun <T> MutableLiveData<List<T>>.delete(item: T) {
    val updatedItems = this.value as ArrayList
    updatedItems.remove(item)
    this.value = updatedItems
}

fun <T> MutableLiveData<T>.notifyObservers(item: T) {
    val value = this.value
    this.value = value
}


fun Long?.timestampToString() : String {

    if (this == null) {
        return String.empty()
    }
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = this

    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH) + 1 // java wtf?
    val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

    return "$dayOfMonth/$month/$year"
}

fun MedicalRecordEntry.deepClone() : MedicalRecordEntry = MedicalRecordEntry(
    id = this.id,
    name = this.name,
    imageUrl = this.imageUrl
)

fun MedicalRecord.deepClone() : MedicalRecord {

    val listOfEntries = ArrayList<MedicalRecordEntry>()

    this.entries.forEach {
        listOfEntries.add(it.deepClone())
    }

    return MedicalRecord(
        id = this.id,
        medicalCategory = this.medicalCategory,
        name = this.name,
        timestamp = this.timestamp,
        entries = listOfEntries
    )
}