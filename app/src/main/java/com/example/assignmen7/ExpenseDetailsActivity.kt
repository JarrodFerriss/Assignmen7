package com.example.assignmen7

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ExpenseDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expense_details)

        // Retrieve passed data
        val expenseName = intent.getStringExtra("expense_name") ?: "Unknown"
        val expenseAmount = intent.getDoubleExtra("expense_amount", 0.0)
        val expenseDate = intent.getStringExtra("expense_date") ?: "No Date"

        // Update UI
        findViewById<TextView>(R.id.expenseNameTextView).text = "Name: $expenseName"
        findViewById<TextView>(R.id.expenseAmountTextView).text = "Amount: $${expenseAmount}"
        findViewById<TextView>(R.id.expenseDateTextView).text = "Date: $expenseDate"

        // Handle Back to Home Button Click
        val backToHomeButton = findViewById<Button>(R.id.backToHomeButton)
        backToHomeButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP // ✅ Closes ExpenseDetailsActivity
            startActivity(intent)
            finish()
        }
    }
}
