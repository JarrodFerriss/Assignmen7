package com.example.assignmen7

import android.os.Bundle
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
    }
}