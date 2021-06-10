package com.example.tatneft_quest.menu

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.InputType
import android.view.*
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.tatneft_quest.R
import com.example.tatneft_quest.databinding.FragmentProfileBinding
import com.google.android.material.datepicker.*
import java.text.SimpleDateFormat
import java.util.*

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private var addedToFavorites = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
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
        (activity as? AppCompatActivity)?.supportActionBar?.title = "Профиль"
        binding.textInputGender.isEndIconVisible = false
        binding.gender.inputType = InputType.TYPE_NULL
        binding.birthday.inputType = InputType.TYPE_NULL
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

                binding.birthday.setOnClickListener {
                    selectBirthday()
                }
                val items = listOf("Мужской", "Женский")
                val adapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, items)
                binding.gender.setAdapter(adapter)
            }
            R.id.save -> {
                noEdited()
                requireActivity().invalidateOptionsMenu()
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
        binding.surname.isFocusable = true
        binding.surname.isCursorVisible = true
        binding.surname.isFocusableInTouchMode = true
        binding.name.isFocusable = true
        binding.name.isCursorVisible = true
        binding.name.isFocusableInTouchMode = true
        binding.patronymic.isFocusable = true
        binding.patronymic.isCursorVisible = true
        binding.patronymic.isFocusableInTouchMode = true
        binding.birthday.isFocusable = true
        binding.birthday.isCursorVisible = true
        binding.birthday.isFocusableInTouchMode = true
        binding.gender.isFocusable = true
        binding.gender.isCursorVisible = true
        binding.gender.isFocusableInTouchMode = true
        binding.textInputGender.isEndIconVisible = true
        binding.city.isFocusable = true
        binding.city.isCursorVisible = true
        binding.city.isFocusableInTouchMode = true
        binding.emailRegistration.isFocusable = true
        binding.emailRegistration.isCursorVisible = true
        binding.emailRegistration.isFocusableInTouchMode = true
        binding.numberRegistration.isFocusable = true
        binding.numberRegistration.isCursorVisible = true
        binding.numberRegistration.isFocusableInTouchMode = true
    }

    private fun noEdited() {
        binding.surname.isFocusable = false
        binding.surname.isCursorVisible = false
        binding.surname.isFocusableInTouchMode = false
        binding.name.isFocusable = false
        binding.name.isCursorVisible = false
        binding.name.isFocusableInTouchMode = false
        binding.patronymic.isFocusable = false
        binding.patronymic.isCursorVisible = false
        binding.patronymic.isFocusableInTouchMode = false
        binding.birthday.isFocusable = false
        binding.birthday.isCursorVisible = false
        binding.birthday.isFocusableInTouchMode = false
        binding.gender.isFocusable = false
        binding.gender.isCursorVisible = false
        binding.gender.isFocusableInTouchMode = false
        binding.textInputGender.isEndIconVisible = false
        binding.city.isFocusable = false
        binding.city.isCursorVisible = false
        binding.city.isFocusableInTouchMode = false
        binding.emailRegistration.isFocusable = false
        binding.emailRegistration.isCursorVisible = false
        binding.emailRegistration.isFocusableInTouchMode = false
        binding.numberRegistration.isFocusable = false
        binding.numberRegistration.isCursorVisible = false
        binding.numberRegistration.isFocusableInTouchMode = false
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
            binding.birthday.setText("${calendar.get(Calendar.DAY_OF_MONTH)}.${calendar.get(Calendar.MONTH) + 1}.${
                calendar.get(Calendar.YEAR)
            }")
        }
        materialDatePicker.show(requireActivity().supportFragmentManager, "MaterialDatePicker")
    }

}