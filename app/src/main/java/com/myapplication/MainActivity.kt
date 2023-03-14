package com.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var numberSystem: NumberSystem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        addCallbacks()
    }

    private fun addCallbacks() {
        binding.buttonCl.setOnClickListener { clearInput() }

        binding.radioGroup.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == binding.radioButtonHex.id) {
                enableFromAToZ()
                enableFromTwoToNine()
                numberSystem = NumberSystem.HEX
            }

            if (checkedId == binding.radioButtonDec.id) {
                disableFromAToZ()
                enableFromTwoToNine()
                numberSystem = NumberSystem.DECIMAL
            }

            if (checkedId == binding.radioButtonOct.id) {
                disableFromAToZ()
                disableEightAndNine()
                numberSystem = NumberSystem.OCTAL
            }

            if (checkedId == binding.radioButtonBin.id) {
                disableFromAToZ()
                disableFromTwoToNine()
                numberSystem = NumberSystem.BIN
            }
        }

        binding.buttonResult.setOnClickListener {

            val textInput = binding.textViewResult.text.toString()
            if (textInput == "") binding.textViewResult.text = ""
            else {
                val inputValue = convertNumberSystemToDecimal(textInput).toString()
                val result = convertDecimalNumberToNumberSystem(inputValue)
                convertBetweenNumberSystems(result)
            }
        }

        binding.buttonDel.setOnClickListener {
            var input = binding.textViewResult.text.toString()
            if (input == "0") input = ""
            input = if (input.length > 1)
                input.dropLast(1)
            else "0"
            binding.textViewResult.text = input
            convertBetweenNumberSystems(input)
        }
    }

    fun onClickNumber(v: View) {
        val newDigit = (v as Button).text.toString().uppercase()
        val oldDigits = binding.textViewResult.text.toString().uppercase()
        val newTextNumber = oldDigits + newDigit
        binding.textViewResult.text = newTextNumber
    }

    private fun clearInput() {
        binding.textViewResult.text = ""
        binding.textViewHex.text = ""
        binding.textViewDec.text = ""
        binding.textViewOct.text = ""
        binding.textViewBin.text = ""
    }

    private fun disableFromTwoToNine () {
        binding.button2.isEnabled = false
        binding.button3.isEnabled = false
        binding.button4.isEnabled = false
        binding.button5.isEnabled = false
        binding.button6.isEnabled = false
        binding.button7.isEnabled = false
        binding.button8.isEnabled = false
        binding.button9.isEnabled = false
    }

    private fun enableFromTwoToNine () {
        binding.button2.isEnabled = true
        binding.button3.isEnabled = true
        binding.button4.isEnabled = true
        binding.button5.isEnabled = true
        binding.button6.isEnabled = true
        binding.button7.isEnabled = true
        binding.button8.isEnabled = true
        binding.button9.isEnabled = true
    }

    private fun disableFromAToZ () {
        binding.buttonA.isEnabled = false
        binding.buttonB.isEnabled = false
        binding.buttonC.isEnabled = false
        binding.buttonD.isEnabled = false
        binding.buttonE.isEnabled = false
        binding.buttonF.isEnabled = false
    }

    private fun enableFromAToZ () {
        binding.buttonA.isEnabled = true
        binding.buttonB.isEnabled = true
        binding.buttonC.isEnabled = true
        binding.buttonD.isEnabled = true
        binding.buttonE.isEnabled = true
        binding.buttonF.isEnabled = true
    }

    private fun disableEightAndNine () {
        binding.button8.isEnabled = false
        binding.button9.isEnabled = false
    }

    private fun convertBetweenNumberSystems(textInput: String) {
        when (numberSystem) {
            NumberSystem.HEX -> {
                val decimal = textInput.toInt(16)
                convertDecimalToNumberSystems(decimal)
            }

            NumberSystem.DECIMAL -> {
                val decimal = textInput.toInt()
                convertDecimalToNumberSystems(decimal)
            }
            NumberSystem.OCTAL -> {
                val decimal = textInput.toInt(8)
                convertDecimalToNumberSystems(decimal)

            }
            NumberSystem.BIN -> {
                val decimal = textInput.toInt(2)
                convertDecimalToNumberSystems(decimal)

            }
        }

    }

    private fun convertDecimalNumberToNumberSystem(input: String): String {
        val decimalValue = input.toInt()
        return when (numberSystem) {
            NumberSystem.DECIMAL -> decimalValue.toString()
            NumberSystem.HEX -> Integer.toHexString(decimalValue)
            NumberSystem.BIN -> Integer.toBinaryString(decimalValue)
            NumberSystem.OCTAL -> Integer.toOctalString(decimalValue)
        }

    }

    private fun convertDecimalToNumberSystems(decimal: Int) {
        binding.textViewHex.text = decimal.toUInt().toString(16)
            .uppercase()
        binding.textViewDec.text = decimal.toString()
        binding.textViewOct.text = decimal.toUInt().toString(8)
        binding.textViewBin.text = decimal.toUInt().toString(2)
    }

    private fun convertNumberSystemToDecimal(textInput: String) = when (numberSystem) {
        NumberSystem.HEX -> Integer.parseInt(textInput, 16)
        NumberSystem.DECIMAL -> textInput.toInt()
        NumberSystem.OCTAL -> Integer.parseInt(textInput, 8)
        NumberSystem.BIN -> Integer.parseInt(textInput, 2)
    }

}