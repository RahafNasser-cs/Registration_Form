package com.example.registrationform

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
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
            if (binding.fullName.text.toString() == "")
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
    }

    private fun updateLable(myCalendar: Calendar) {
        val myFormat = "dd-MM-yyy"
        val sdf = SimpleDateFormat(myFormat, Locale.UK)
        binding.dateOfBirth.setText(sdf.format(myCalendar.time))
    }

    fun registration() {
        var fullName = binding.fullName.text.toString()
        var email = binding.email.text.toString()
        var dateOfBirth = binding.dateOfBirth.text.toString()
        var gender = when (binding.genderOptions.checkedRadioButtonId) {
            binding.optionFemale.id -> "Female"
            binding.optionMale.id -> "Male"
            else -> ""
        }
        var isConfirmPass = isValidPassword()
        binding.infoTxtview.setText("Name: $fullName  Email: $email\nBirthday: $dateOfBirth  Gender: $gender\npassword Match: $isConfirmPass")

    }

    fun isValidPassword(): Boolean {
        var password = binding.password.text.toString().trim()
        var confirmPassword = binding.reEnterPassword.text.toString().trim()
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
    }
}