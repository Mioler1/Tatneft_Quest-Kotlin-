package com.example.tatneft_quest.firstActivity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.tatneft_quest.R
import com.example.tatneft_quest.Variables.Companion.SAVE_DATA_USER
import com.example.tatneft_quest.Variables.Companion.SAVE_DATA_USER_AVATAR
import com.example.tatneft_quest.Variables.Companion.SAVE_DATA_USER_BIRTHDAY
import com.example.tatneft_quest.Variables.Companion.SAVE_DATA_USER_CITY
import com.example.tatneft_quest.Variables.Companion.SAVE_DATA_USER_EMAIL
import com.example.tatneft_quest.Variables.Companion.SAVE_DATA_USER_GENDER
import com.example.tatneft_quest.Variables.Companion.SAVE_DATA_USER_LOGIN
import com.example.tatneft_quest.Variables.Companion.SAVE_DATA_USER_NAME
import com.example.tatneft_quest.Variables.Companion.SAVE_DATA_USER_NUMBER
import com.example.tatneft_quest.Variables.Companion.SAVE_DATA_USER_PASSWORD
import com.example.tatneft_quest.Variables.Companion.SAVE_DATA_USER_PATRONYMIC
import com.example.tatneft_quest.Variables.Companion.SAVE_DATA_USER_SURNAME
import com.example.tatneft_quest.databinding.ActivityRegistrationBinding
import com.google.android.material.datepicker.*
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import de.hdodenhof.circleimageview.CircleImageView
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*

@Suppress("DEPRECATION")
class RegistrationActivity : AppCompatActivity() {
    private lateinit var emailRegistration: TextInputEditText
    private lateinit var passwordRegistration: TextInputEditText
    private lateinit var repeatPasswordRegistration: TextInputEditText
    private lateinit var surname: TextInputEditText
    private lateinit var name: TextInputEditText
    private lateinit var patronymic: TextInputEditText
    private lateinit var birthday: TextInputEditText
    private lateinit var gender: AutoCompleteTextView
    private lateinit var city: TextInputEditText
    private lateinit var numberRegistration: TextInputEditText
    private lateinit var login: TextInputEditText
    private lateinit var avatar: CircleImageView
    private lateinit var regButton: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var helper: ImageButton

    private lateinit var textInputSurname: TextInputLayout
    private lateinit var textInputName: TextInputLayout
    private lateinit var textInputPatronymic: TextInputLayout
    private lateinit var textInputBirthday: TextInputLayout
    private lateinit var textInputGender: TextInputLayout
    private lateinit var textInputCity: TextInputLayout
    private lateinit var textInputEmail: TextInputLayout
    private lateinit var textInputNumber: TextInputLayout
    private lateinit var textInputLogin: TextInputLayout
    private lateinit var textInputPassword: TextInputLayout
    private lateinit var textInputRepeatPassword: TextInputLayout

    private lateinit var binding: ActivityRegistrationBinding
    private var byteString: String? = null

    private fun init() {
        emailRegistration = binding.emailRegistration
        passwordRegistration = binding.passwordRegistration
        repeatPasswordRegistration = binding.repeatPasswordRegistration
        surname = binding.surname
        name = binding.name
        patronymic = binding.patronymic
        birthday = binding.birthday!!
        gender = binding.gender
        city = binding.city
        numberRegistration = binding.numberRegistration
        login = binding.login
        avatar = binding.avatar
        regButton = binding.regButton
        progressBar = binding.progressBar!!
        helper = binding.helper

        gender.inputType = InputType.TYPE_NULL
        birthday.inputType = InputType.TYPE_NULL

        textInputSurname = binding.textInputSurname
        textInputName = binding.textInputName
        textInputPatronymic = binding.textInputPatronymic
        textInputBirthday = binding.textInputBirthday!!
        textInputGender = binding.textInputGender
        textInputCity = binding.textInputCity
        textInputEmail = binding.textInputEmail
        textInputNumber = binding.textInputNumber
        textInputLogin = binding.textInputLogin
        textInputPassword = binding.textInputPassword
        textInputRepeatPassword = binding.textInputRepeatPassword
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()

        val items = listOf("Мужской", "Женский")
        val adapter = ArrayAdapter(this, R.layout.dropdown_item, items)
        gender.setAdapter(adapter)

        binding.authButton.setOnClickListener {
            super.onBackPressed()
        }
        regButton.setOnClickListener {
            onStartRegistration()
        }
        avatar.setOnClickListener {
            pickImageFromGallery()
        }

        helper.setOnClickListener {
            Snackbar.make(it,
                "Латиница, кириллица, цифра, от 6 символов, @\$#?_.-",
                Snackbar.LENGTH_LONG).show()

        }

        birthday.setOnClickListener {
            selectBirthday()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun selectBirthday() {
        val calendarStart = Calendar.getInstance()
        val calendarEnd = Calendar.getInstance()
        val year = SimpleDateFormat("yyyy", Locale.getDefault()).format(Date()).toInt()
        val month = SimpleDateFormat("MM", Locale.getDefault()).format(Date()).toInt() - 1
        val day = SimpleDateFormat("dd", Locale.getDefault()).format(Date()).toInt() - 1
        calendarStart[year - 100, month] = day
        calendarEnd[year, month] = day

        val constraintsBuilder = CalendarConstraints.Builder()
        val validators: ArrayList<CalendarConstraints.DateValidator> = ArrayList()
        validators.add(DateValidatorPointForward.from(calendarStart.timeInMillis))
        validators.add(DateValidatorPointBackward.before(calendarEnd.timeInMillis))
        constraintsBuilder.setValidator(CompositeDateValidator.allOf(validators))

        val materialDatePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Выберите дату рождения")
            .setInputMode(MaterialDatePicker.INPUT_MODE_CALENDAR)
            .setTheme(R.style.myMaterialCalendarHeaderToggleButton)
            .setCalendarConstraints(constraintsBuilder.build())
            .build()

        materialDatePicker.addOnPositiveButtonClickListener {
            val calendar = Calendar.getInstance()
            calendar.time = Date(it)
            birthday.setText("${calendar.get(Calendar.DAY_OF_MONTH)}.${calendar.get(Calendar.MONTH) + 1}.${
                calendar.get(Calendar.YEAR)
            }")
        }
        materialDatePicker.show(supportFragmentManager, "MaterialDatePicker")
    }

    private fun onStartRegistration() {
        val emailText = emailRegistration.text.toString()
        val passwordText = passwordRegistration.text.toString()
        val repeatPassword = repeatPasswordRegistration.text.toString()
        val surnameText = surname.text.toString()
        val nameText = name.text.toString()
        val patronymicText = patronymic.text.toString()
        val birthdayText = birthday.text.toString()
        val genderText = gender.text.toString()
        val cityText = city.text.toString()
        val numberText = numberRegistration.text.toString()
        val loginText = login.text.toString()
        var check = false

        clearTextInputLayout()
        if (surnameText.isEmpty()) {
            textInputSurname.error = "Пустое поле"
            textInputSurname.requestFocus()
            return
        }
        if (nameText.isEmpty()) {
            textInputName.error = "Пустое поле"
            textInputName.requestFocus()
            return
        }
        if (patronymicText.isEmpty()) {
            textInputPatronymic.error = "Пустое поле"
            textInputPatronymic.requestFocus()
            return
        }
        if (birthdayText.isEmpty()) {
            textInputBirthday.error = "Пустое поле"
            textInputBirthday.requestFocus()
            return
        }
        if (genderText.isEmpty()) {
            textInputGender.error = "Пустое поле"
            textInputGender.requestFocus()
            return
        }
        if (cityText.isEmpty()) {
            textInputCity.error = "Пустое поле"
            textInputCity.requestFocus()
            return
        }
        if (emailText.isEmpty()) {
            textInputEmail.error = "Пустое поле"
            textInputEmail.requestFocus()
            return
        }
        if (numberText.isEmpty()) {
            textInputNumber.error = "Пустое поле"
            textInputNumber.requestFocus()
            return
        }
        if (numberText.length != 10) {
            textInputNumber.error = "Номер телефона введен неверно"
            textInputNumber.requestFocus()
            return
        }
        if (loginText.isEmpty()) {
            textInputLogin.error = "Пустое поле"
            textInputLogin.requestFocus()
            return
        }
        if (passwordText.isEmpty()) {
            textInputPassword.error = "Пустое поле"
            textInputPassword.requestFocus()
            return
        }
        if (passwordText.length < 6) {
            textInputPassword.error = "Минимум 6 символов"
            textInputPassword.requestFocus()
            return
        }
        if (passwordText.matches("[a-zA-Zа-яА-Я0-9@$#?_.-]+".toRegex())) {
            check = true
        }
        if (!check) {
            textInputPassword.error = "Латиница, кириллица, цифра, от 6 символов, @\$#?_.-"
            textInputPassword.requestFocus()
            return
        }
        if (repeatPassword.isEmpty()) {
            textInputRepeatPassword.error = "Пустое поле"
            textInputRepeatPassword.requestFocus()
            return
        }
        if (passwordText != repeatPassword) {
            textInputRepeatPassword.error = "Пароль не совпадают"
            textInputRepeatPassword.requestFocus()
            return
        }
        if (byteString == null) {
            val bitmap: Bitmap = (avatar.drawable as BitmapDrawable).bitmap
            val byteArrayOutputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
            val byteArray = byteArrayOutputStream.toByteArray()
            byteString = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Base64.getEncoder().encodeToString(byteArray)
            } else {
                android.util.Base64.encodeToString(byteArray, android.util.Base64.DEFAULT)
            }
        }
        getSharedPreferences(SAVE_DATA_USER, MODE_PRIVATE).edit().also {
            it.putString(SAVE_DATA_USER_EMAIL, emailText)
                .putString(SAVE_DATA_USER_PASSWORD, passwordText)
                .putString(SAVE_DATA_USER_SURNAME, surnameText)
                .putString(SAVE_DATA_USER_NAME, nameText)
                .putString(SAVE_DATA_USER_PATRONYMIC, patronymicText)
                .putString(SAVE_DATA_USER_BIRTHDAY, birthdayText)
                .putString(SAVE_DATA_USER_GENDER, genderText)
                .putString(SAVE_DATA_USER_CITY, cityText)
                .putString(SAVE_DATA_USER_NUMBER, numberText)
                .putString(SAVE_DATA_USER_LOGIN, loginText)
                .putString(SAVE_DATA_USER_AVATAR, byteString)
                .apply()
        }

        val intent = Intent(this, AuthorizationActivity::class.java)
        intent.putExtra("email", emailText)
        intent.putExtra("password", passwordText)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_PICK_CODE && data != null && data.data != null) {
            if (resultCode == Activity.RESULT_OK) {
                progressBar.visibility = View.VISIBLE
                avatar.setImageURI(data.data)
                regButton.setBackgroundResource(R.drawable.background_button_disable)
                regButton.isEnabled = false
                uploadImage()
            }
        }
    }

    private fun uploadImage() {
        val bitmap: Bitmap = (avatar.drawable as BitmapDrawable).bitmap
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        if (byteArray.size <= 5242880) {
            byteString = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Base64.getEncoder().encodeToString(byteArray)
            } else {
                android.util.Base64.encodeToString(byteArray, android.util.Base64.DEFAULT)
            }
            regButton.isEnabled = true
            regButton.setBackgroundResource(R.drawable.background_button_green)
            progressBar.visibility = View.GONE
        } else {
            Toast.makeText(this, "Размер картинки не более 5мб", Toast.LENGTH_SHORT).show()
            avatar.setImageResource(R.drawable.default_avatar)
            regButton.isEnabled = true
            regButton.setBackgroundResource(R.drawable.background_button_green)
            progressBar.visibility = View.GONE
        }
    }

    private fun clearTextInputLayout() {
        textInputSurname.error = null
        textInputName.error = null
        textInputPatronymic.error = null
        textInputBirthday.error = null
        textInputGender.error = null
        textInputCity.error = null
        textInputEmail.error = null
        textInputNumber.error = null
        textInputLogin.error = null
        textInputPassword.error = null
        textInputRepeatPassword.error = null
    }

    companion object {
        const val IMAGE_PICK_CODE = 1000
    }
}