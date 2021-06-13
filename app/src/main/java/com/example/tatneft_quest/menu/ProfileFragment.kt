package com.example.tatneft_quest.menu

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Bundle
import android.text.InputType
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.tatneft_quest.R
import com.example.tatneft_quest.databinding.FragmentProfileBinding
import com.example.tatneft_quest.firstActivity.RegistrationActivity
import com.google.android.material.datepicker.*
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import de.hdodenhof.circleimageview.CircleImageView
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private var addedToFavorites = false
    private lateinit var progressBar: ProgressBar
    private lateinit var avatar: CircleImageView
    private var byteString: String? = null

    private lateinit var emailRegistration: TextInputEditText
    private lateinit var surname: TextInputEditText
    private lateinit var name: TextInputEditText
    private lateinit var patronymic: TextInputEditText
    private lateinit var birthday: TextInputEditText
    private lateinit var gender: AutoCompleteTextView
    private lateinit var city: TextInputEditText
    private lateinit var numberRegistration: TextInputEditText

    private lateinit var textInputGender: TextInputLayout

    fun init() {
        emailRegistration = binding.emailRegistration
        surname = binding.surname
        name = binding.name
        patronymic = binding.patronymic
        birthday = binding.birthday
        gender = binding.gender
        city = binding.city
        numberRegistration = binding.numberRegistration
        avatar = binding.avatar
        progressBar = binding.progressBar!!

        gender.inputType = InputType.TYPE_NULL
        birthday.inputType = InputType.TYPE_NULL
        textInputGender = binding.textInputGender
    }

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
        setHasOptionsMenu(true)
        init()
        (activity as? AppCompatActivity)?.supportActionBar?.title = "Профиль"
        textInputGender.isEndIconVisible = false
        gender.inputType = InputType.TYPE_NULL
        birthday.inputType = InputType.TYPE_NULL

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.edit -> {
                edited()
                requireActivity().invalidateOptionsMenu()

                birthday.setOnClickListener {
                    selectBirthday()
                }
                val items = listOf("Мужской", "Женский")
                val adapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, items)
                gender.setAdapter(adapter)

                avatar.setOnClickListener {
                    pickImageFromGallery()
                }
            }
            R.id.save -> {
                noEdited()
                requireActivity().invalidateOptionsMenu()
                hideKeyboard()

            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        addedToFavorites = if (!addedToFavorites) {
            val mi = menu.findItem(R.id.save)
            menu.removeItem(R.id.save)
            mi.setIcon(R.drawable.ic_edit)
            mi.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM)
            true
        } else {
            val mi = menu.findItem(R.id.edit)
            menu.removeItem(R.id.edit)
            mi.setIcon(R.drawable.ic_check)
            mi.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM)
            false
        }
        return super.onPrepareOptionsMenu(menu)
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
        birthday.isFocusableInTouchMode = true
        gender.isFocusable = true
        gender.isCursorVisible = true
        gender.isFocusableInTouchMode = true
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
            byteString = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Base64.getEncoder().encodeToString(byteArray)
            } else {
                android.util.Base64.encodeToString(byteArray, android.util.Base64.DEFAULT)
            }
            progressBar.visibility = View.GONE
        } else {
            Toast.makeText(requireContext(), "Размер картинки не более 5мб", Toast.LENGTH_SHORT)
                .show()
            avatar.setImageResource(R.drawable.default_avatar)
            progressBar.visibility = View.GONE
        }
    }

    private fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

    private fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    companion object {
        const val IMAGE_PICK_CODE = 1000
    }
}