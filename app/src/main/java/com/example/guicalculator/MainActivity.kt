package com.example.guicalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import kotlin.math.sqrt

class MainActivity : AppCompatActivity() {

    lateinit var editText: EditText
    var operand1: Double? = null
    var operand2: Double? = null
    var pendingOperation = "="

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editText = findViewById(R.id.editTextNumber)

        // Initialize buttons
        val button0 = findViewById<Button>(R.id.button0)
        val button1 = findViewById<Button>(R.id.button1)
        val button2 = findViewById<Button>(R.id.button2)
        val button3 = findViewById<Button>(R.id.button3)
        val button4 = findViewById<Button>(R.id.button4)
        val button5 = findViewById<Button>(R.id.button5)
        val button6 = findViewById<Button>(R.id.button6)
        val button7 = findViewById<Button>(R.id.button7)
        val button8 = findViewById<Button>(R.id.button8)
        val button9 = findViewById<Button>(R.id.button9)
        val buttonDot = findViewById<Button>(R.id.buttonDot)

        val buttonAdd = findViewById<Button>(R.id.buttonAdd)
        val buttonSubtract = findViewById<Button>(R.id.buttonSubtract)
        val buttonMultiply = findViewById<Button>(R.id.buttonMultiply)
        val buttonDivide = findViewById<Button>(R.id.buttonDivide)
        val buttonSqrt = findViewById<Button>(R.id.buttonSqrt)
        val buttonEquals = findViewById<Button>(R.id.buttonEquals)

        // Set onClickListeners for each button
        val listener = View.OnClickListener { v ->
            val b = v as Button
            editText.append(b.text)
        }

        button0.setOnClickListener(listener)
        button1.setOnClickListener(listener)
        button2.setOnClickListener(listener)
        button3.setOnClickListener(listener)
        button4.setOnClickListener(listener)
        button5.setOnClickListener(listener)
        button6.setOnClickListener(listener)
        button7.setOnClickListener(listener)
        button8.setOnClickListener(listener)
        button9.setOnClickListener(listener)
        buttonDot.setOnClickListener(listener)

        // Listener for operation buttons
        val opListener = View.OnClickListener { v ->
            val op = (v as Button).text.toString()
            try {
                val value = editText.text.toString().toDouble()
                performOperation(value, op)
            } catch (e: NumberFormatException) {
                editText.setText("")
            }
            pendingOperation = op
        }

        buttonAdd.setOnClickListener(opListener)
        buttonSubtract.setOnClickListener(opListener)
        buttonMultiply.setOnClickListener(opListener)
        buttonDivide.setOnClickListener(opListener)
        buttonEquals.setOnClickListener(opListener)

        // Special handling for the sqrt button
        buttonSqrt.setOnClickListener {
            val value = editText.text.toString().toDoubleOrNull()
            value?.let {
                performOperation(it, "sqrt")
            }
        }
    }

    private fun performOperation(value: Double, operation: String) {
        if (operation == "sqrt") {
            operand1 = sqrt(value)
        } else if (operand1 == null) {
            operand1 = value
        } else {
            if (pendingOperation == "=") {
                pendingOperation = operation
            }
            when (pendingOperation) {
                "=" -> operand1 = value
                "/" -> operand1 = if (value == 0.0) {
                    Double.NaN // Handle divide by zero
                } else {
                    operand1!! / value
                }
                "*" -> operand1 = operand1!! * value
                "-" -> operand1 = operand1!! - value
                "+" -> operand1 = operand1!! + value
                "sqrt" -> operand1 = sqrt(value)
            }
        }

        editText.setText(operand1.toString())
//        if (operation != "sqrt") {
//            pendingOperation = operation
//        }

        if (operation == "=") {
            operand1 = null
        } else if (operation != "sqrt"){
            editText.setText("")
        }
    }

}



