package com.example.tatneft_quest.menu

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.tatneft_quest.R
import com.example.tatneft_quest.Variables
import com.example.tatneft_quest.Variables.Companion.SAVE_DATA_USER
import com.example.tatneft_quest.Variables.Companion.SAVE_DATA_USER_AVATAR
import com.example.tatneft_quest.Variables.Companion.SAVE_DATA_USER_BIRTHDAY
import com.example.tatneft_quest.Variables.Companion.SAVE_DATA_USER_CITY
import com.example.tatneft_quest.Variables.Companion.SAVE_DATA_USER_EMAIL
import com.example.tatneft_quest.Variables.Companion.SAVE_DATA_USER_GENDER
import com.example.tatneft_quest.Variables.Companion.SAVE_DATA_USER_NAME
import com.example.tatneft_quest.Variables.Companion.SAVE_DATA_USER_NUMBER
import com.example.tatneft_quest.Variables.Companion.SAVE_DATA_USER_PATRONYMIC
import com.example.tatneft_quest.Variables.Companion.SAVE_DATA_USER_SURNAME
import com.example.tatneft_quest.Variables.Companion.TAG
import com.example.tatneft_quest.databinding.FragmentProfileBinding
import com.example.tatneft_quest.firstActivity.RegistrationActivity
import com.google.android.material.datepicker.*
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import de.hdodenhof.circleimageview.CircleImageView
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*

@Suppress("DEPRECATION")
class ProfileFragment : Fragment() {
    private var addedToFavorites: Boolean? = null
    private var byteString: String? = null
    private lateinit var progressBar: ProgressBar
    private lateinit var avatar: CircleImageView

    private lateinit var emailRegistration: TextInputEditText
    private lateinit var surname: TextInputEditText
    private lateinit var name: TextInputEditText
    private lateinit var patronymic: TextInputEditText
    private lateinit var birthday: TextInputEditText
    private lateinit var gender: AutoCompleteTextView
    private lateinit var city: TextInputEditText
    private lateinit var numberRegistration: TextInputEditText
    private lateinit var textInputGender: TextInputLayout

    private lateinit var textInputSurname: TextInputLayout
    private lateinit var textInputName: TextInputLayout
    private lateinit var textInputPatronymic: TextInputLayout
    private lateinit var textInputBirthday: TextInputLayout
    private lateinit var textInputCity: TextInputLayout
    private lateinit var textInputEmail: TextInputLayout
    private lateinit var textInputNumber: TextInputLayout

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentProfileBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addedToFavorites = savedInstanceState?.getBoolean("IconToolbar") ?: false
        (activity as? AppCompatActivity)?.supportActionBar?.title = "Профиль"
        setHasOptionsMenu(true)
        init()
        downloadData()
    }

    private fun init() {
        sharedPreferences = requireActivity().getSharedPreferences(SAVE_DATA_USER, MODE_PRIVATE)
        emailRegistration = binding.emailRegistration
        surname = binding.surname
        name = binding.name
        patronymic = binding.patronymic
        birthday = binding.birthday
        gender = binding.gender
        city = binding.city
        numberRegistration = binding.numberRegistration
        avatar = binding.avatar
        progressBar = binding.progressBar
        textInputGender = binding.textInputGender

        textInputSurname = binding.textInputSurname
        textInputName = binding.textInputName
        textInputPatronymic = binding.textInputPatronymic
        textInputBirthday = binding.textInputBirthday
        textInputGender = binding.textInputGender
        textInputCity = binding.textInputCity
        textInputEmail = binding.textInputEmail
        textInputNumber = binding.textInputNumber

        gender.inputType = InputType.TYPE_NULL
        birthday.inputType = InputType.TYPE_NULL
        textInputGender.isEndIconVisible = false
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.edit -> {
                addedToFavorites = true
                requireActivity().invalidateOptionsMenu()
            }
            R.id.save -> {
                addedToFavorites = false
                requireActivity().invalidateOptionsMenu()
                saveData()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        if (!addedToFavorites!!) {
            val mi = menu.findItem(R.id.save)
            menu.removeItem(R.id.save)
            mi.setIcon(R.drawable.ic_edit)
            mi.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM)
            noEdited()
            hideKeyboard()
        } else {
            val mi = menu.findItem(R.id.edit)
            menu.removeItem(R.id.edit)
            mi.setIcon(R.drawable.ic_check)
            mi.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM)
            edited()
            birthday.setOnClickListener {
                selectBirthday()
            }
            val items = listOf("Мужской", "Женский")
            val adapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, items)
            gender.setAdapter(adapter)
            gender.setOnClickListener {
                hideKeyboard()
            }
            avatar.setOnClickListener {
                pickImageFromGallery()
            }
        }
        return super.onPrepareOptionsMenu(menu)
    }

    private fun downloadData() {
        surname.setText(sharedPreferences.getString(SAVE_DATA_USER_SURNAME, ""))
        name.setText(sharedPreferences.getString(SAVE_DATA_USER_NAME, ""))
        patronymic.setText(sharedPreferences.getString(SAVE_DATA_USER_PATRONYMIC, ""))
        birthday.setText(sharedPreferences.getString(SAVE_DATA_USER_BIRTHDAY, ""))
        gender.setText(sharedPreferences.getString(SAVE_DATA_USER_GENDER, ""))
        city.setText(sharedPreferences.getString(SAVE_DATA_USER_CITY, ""))
        emailRegistration.setText(sharedPreferences.getString(SAVE_DATA_USER_EMAIL, ""))
        numberRegistration.setText(sharedPreferences.getString(SAVE_DATA_USER_NUMBER, ""))
        byteString = sharedPreferences.getString(SAVE_DATA_USER_AVATAR, "")
        val byteArray = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Base64.getDecoder().decode(byteString)
        } else {
            android.util.Base64.decode(byteString, android.util.Base64.DEFAULT)
        }
        val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
        avatar.setImageBitmap(bitmap)
    }

    private fun saveData() {
        val emailText = emailRegistration.text.toString()
        val surnameText = surname.text.toString()
        val nameText = name.text.toString()
        val patronymicText = patronymic.text.toString()
        val birthdayText = birthday.text.toString()
        val genderText = gender.text.toString()
        val cityText = city.text.toString()
        val numberText = numberRegistration.text.toString()

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

        if (byteString == "") {
            val bitmap: Bitmap = (avatar.drawable as BitmapDrawable).bitmap
            val byteArrayOutputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
            val byteArray = byteArrayOutputStream.toByteArray()
            transformationImage(byteArray)
        }

        Log.d(TAG, "saveData: $byteString")

        requireActivity().getSharedPreferences(SAVE_DATA_USER, MODE_PRIVATE).edit().also {
            it.putString(SAVE_DATA_USER_EMAIL, emailText)
                .putString(SAVE_DATA_USER_SURNAME, surnameText)
                .putString(SAVE_DATA_USER_NAME, nameText)
                .putString(SAVE_DATA_USER_PATRONYMIC, patronymicText)
                .putString(SAVE_DATA_USER_BIRTHDAY, birthdayText)
                .putString(SAVE_DATA_USER_GENDER, genderText)
                .putString(SAVE_DATA_USER_CITY, cityText)
                .putString(SAVE_DATA_USER_NUMBER, numberText)
                .putString(SAVE_DATA_USER_AVATAR, byteString)
                .apply()
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
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (byteString != null) {
            outState.putString("Image", byteString)
        }
        outState.putBoolean("IconToolbar", addedToFavorites!!)
    }

    private fun edited() {
        surname.isFocusable = true
        surname.isCursorVisible = true
        surname.isFocusableInTouchMode = true
        name.isFocusable = true
        name.isCursorVisible = true
        name.isFocusableInTouchMode = true
        patronymic.isFocusable = true
        patronymic.isCursorVisible = true
        patronymic.isFocusableInTouchMode = true
        birthday.isFocusable = true
        birthday.isCursorVisible = true
        birthday.isEnabled = true
        gender.isFocusable = true
        gender.isCursorVisible = true
        gender.isFocusableInTouchMode = true
        gender.isEnabled = true
        textInputGender.isEndIconVisible = true
        city.isFocusable = true
        city.isCursorVisible = true
        city.isFocusableInTouchMode = true
        emailRegistration.isFocusable = true
        emailRegistration.isCursorVisible = true
        emailRegistration.isFocusableInTouchMode = true
        numberRegistration.isFocusable = true
        numberRegistration.isCursorVisible = true
        numberRegistration.isFocusableInTouchMode = true
        avatar.isClickable = true
    }

    private fun noEdited() {
        surname.isFocusable = false
        surname.isCursorVisible = false
        surname.isFocusableInTouchMode = false
        name.isFocusable = false
        name.isCursorVisible = false
        name.isFocusableInTouchMode = false
        patronymic.isFocusable = false
        patronymic.isCursorVisible = false
        patronymic.isFocusableInTouchMode = false
        birthday.isFocusable = false
        birthday.isCursorVisible = false
        birthday.isEnabled = false
        birthday.isFocusableInTouchMode = false
        gender.isFocusable = false
        gender.isCursorVisible = false
        gender.isFocusableInTouchMode = false
        gender.isEnabled = false
        textInputGender.isEndIconVisible = false
        city.isFocusable = false
        city.isCursorVisible = false
        city.isFocusableInTouchMode = false
        emailRegistration.isFocusable = false
        emailRegistration.isCursorVisible = false
        emailRegistration.isFocusableInTouchMode = false
        numberRegistration.isFocusable = false
        numberRegistration.isCursorVisible = false
        numberRegistration.isFocusableInTouchMode = false
        avatar.isClickable = false
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
        materialDatePicker.show(requireActivity().supportFragmentManager, "MaterialDatePicker")
    }

    private fun pickImageFromGallery() {
        byteString = ""
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RegistrationActivity.IMAGE_PICK_CODE && data != null && data.data != null) {
            if (resultCode == Activity.RESULT_OK) {
                progressBar.visibility = View.VISIBLE
                avatar.setImageURI(data.data)
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
            transformationImage(byteArray)
            progressBar.visibility = View.GONE
        } else {
            Toast.makeText(context, "Размер картинки не более 5мб", Toast.LENGTH_SHORT).show()
            avatar.setImageResource(R.drawable.default_avatar)
            progressBar.visibility = View.GONE
        }

    }

    private fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

    private fun Context.hideKeyboard(view: View) {
        val inputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun transformationImage(byteArray: ByteArray) {
        byteString = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Base64.getEncoder().encodeToString(byteArray)
        } else {
            android.util.Base64.encodeToString(byteArray, android.util.Base64.DEFAULT)
        }
    }

    companion object {
        const val IMAGE_PICK_CODE = 1000
    }
}