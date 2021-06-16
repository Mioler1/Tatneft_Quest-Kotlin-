package com.example.tatneft_quest.menu

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
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.tatneft_quest.R
import com.example.tatneft_quest.Variables.Companion.SAVE_DATA_USER
import com.example.tatneft_quest.Variables.Companion.SAVE_DATA_USER_AVATAR
import com.example.tatneft_quest.Variables.Companion.SAVE_DATA_USER_CITY
import com.example.tatneft_quest.Variables.Companion.SAVE_DATA_USER_EMAIL
import com.example.tatneft_quest.Variables.Companion.SAVE_DATA_USER_NAME
import com.example.tatneft_quest.Variables.Companion.SAVE_DATA_USER_NUMBER
import com.example.tatneft_quest.Variables.Companion.SAVE_DATA_USER_PATRONYMIC
import com.example.tatneft_quest.Variables.Companion.SAVE_DATA_USER_SURNAME
import com.example.tatneft_quest.Variables.Companion.TAG
import com.example.tatneft_quest.databinding.FragmentProfileBinding
import com.example.tatneft_quest.firstActivity.RegistrationActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import de.hdodenhof.circleimageview.CircleImageView
import java.io.ByteArrayOutputStream
import java.util.*
import java.util.regex.Pattern

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
    private lateinit var city: TextInputEditText
    private lateinit var numberRegistration: TextInputEditText

    private lateinit var textInputSurname: TextInputLayout
    private lateinit var textInputName: TextInputLayout
    private lateinit var textInputPatronymic: TextInputLayout
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
        city = binding.city
        numberRegistration = binding.numberRegistration
        avatar = binding.avatar
        progressBar = binding.progressBar

        textInputSurname = binding.textInputSurname
        textInputName = binding.textInputName
        textInputPatronymic = binding.textInputPatronymic
        textInputCity = binding.textInputCity
        textInputEmail = binding.textInputEmail
        textInputNumber = binding.textInputNumber

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

    private fun isValidEmailAddress(email: String): Boolean {
        val ePattern =
            "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$"
        val p = Pattern.compile(ePattern)
        val m = p.matcher(email)
        return m.matches()
    }

    private fun saveData() {
        val emailText = emailRegistration.text.toString()
        val surnameText = surname.text.toString()
        val nameText = name.text.toString()
        val patronymicText = patronymic.text.toString()
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
        if (!isValidEmailAddress(emailText)) {
            textInputEmail.error = "Некорретная почта"
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

        requireActivity().getSharedPreferences(SAVE_DATA_USER, MODE_PRIVATE).edit().also {
            it.putString(SAVE_DATA_USER_EMAIL, emailText)
                .putString(SAVE_DATA_USER_SURNAME, surnameText)
                .putString(SAVE_DATA_USER_NAME, nameText)
                .putString(SAVE_DATA_USER_PATRONYMIC, patronymicText)
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