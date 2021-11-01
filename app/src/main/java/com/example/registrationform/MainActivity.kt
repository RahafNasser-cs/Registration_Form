package com.example.registrationform

import android.app.DatePickerDialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.example.registrationform.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val myCalendar = Calendar.getInstance()
        val datePicker = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateLable(myCalendar)
        }

        binding.datePickerBtn.setOnClickListener{
            DatePickerDialog(this, datePicker, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show()
        }
        binding.registrationBtn.setOnClickListener {
            if (binding.fullNameEditText.text.toString() == "")
                Toast.makeText(MainActivity@ this, "Enter valid name", Toast.LENGTH_LONG).show()
            else if (!isValidPassword())
                Toast.makeText(MainActivity@ this, "Enter valid password", Toast.LENGTH_LONG).show()
            //else if (!isValidEmail())
                //Toast.makeText(MainActivity@ this, "Enter valid email", Toast.LENGTH_LONG).show()
            else if (!isCheckRadio())
                Toast.makeText(MainActivity@ this, "Please choose your gender", Toast.LENGTH_LONG).show()
            else
                registration()
        }

        /*binding.fullNameEditText.setOnKeyListener { view, keyCode, _ -> handleKeyEvent(view, keyCode)
        }
        binding.emailEditText.setOnKeyListener { view, keyCode, _ -> handleKeyEvent(view, keyCode)
        }
        binding.passwordEditText.setOnKeyListener { view, keyCode, _ -> handleKeyEvent(view, keyCode)
        }
        binding.reEnterPasswordEditText.setOnKeyListener { view, keyCode, _ -> handleKeyEvent(view, keyCode)
        }
        binding.dateOfBirthEditText.setOnKeyListener { view, keyCode, _ -> handleKeyEvent(view, keyCode)
        }*/
    }

    private fun updateLable(myCalendar: Calendar) {
        val myFormat = "dd-MM-yyy"
        val sdf = SimpleDateFormat(myFormat, Locale.UK)
        binding.dateOfBirthEditText.setText(sdf.format(myCalendar.time))
    }

    fun registration() {
        var fullName = binding.fullNameEditText.text.toString()
        var email = binding.emailEditText.text.toString()
        var dateOfBirth = binding.dateOfBirthEditText.text.toString()
        var gender = when (binding.genderOptions.checkedRadioButtonId) {
            binding.optionFemale.id -> "Female"
            binding.optionMale.id -> "Male"
            else -> ""
        }
        var isConfirmPass = isValidPassword()
        binding.infoTxtview.setText("Name: $fullName  Email: $email\nBirthday: $dateOfBirth  Gender: $gender\npassword Match: $isConfirmPass")

    }

    fun isValidPassword(): Boolean {
        var password = binding.passwordEditText.text.toString().trim()
        var confirmPassword = binding.reEnterPasswordEditText.text.toString().trim()
        return password.equals(confirmPassword)
    }
    //working on
    fun isValidEmail(): Boolean {
        var mailInput = binding.email.toString().trim()

        return if (mailInput.isEmpty())
            false
        else
            android.util.Patterns.EMAIL_ADDRESS.matcher(mailInput).matches()

    }
    fun isCheckRadio(): Boolean {
        var radio = binding.genderOptions.checkedRadioButtonId
        return when (radio) {
            binding.optionFemale.id -> true
            binding.optionMale.id -> true
            else -> false
        }
    }/*
    fun isValidEmail2(): Boolean {
        var mailInput = binding.email.toString().trim()
        var emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\\\.+[a-z]+"

        binding.email.addTextChangedListener(  TextWatcher() {
            fun addTextChangedListener () {

            }
        })
        return if (mailInput.isEmpty())
            false
        else
            android.util.Patterns.EMAIL_ADDRESS.matcher(mailInput).matches()

    }*/
    private fun handleKeyEvent(view: View, keyCode: Int): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            // Hide the keyboard
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            return true
        }
        return false
    }
}