package com.example.assignmen7

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.assignmen7.adapter.ExpenseAdapter
import com.example.assignmen7.model.Expense
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import java.util.Calendar
import android.net.Uri
import android.widget.Button

class MainActivity : AppCompatActivity() {

    private val expenseList = mutableListOf<Expense>()
    private lateinit var adapter: ExpenseAdapter

    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG, "onCreate called") // Log onCreate event

        val nameInput = findViewById<TextInputEditText>(R.id.expenseName)
        val amountInput = findViewById<TextInputEditText>(R.id.expenseAmount)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val addButton = findViewById<FloatingActionButton>(R.id.fab)
        val datePicker = findViewById<TextInputEditText>(R.id.expenseDate)
        val openBrowserButton = findViewById<Button>(R.id.openBrowserButton)

        adapter = ExpenseAdapter(
            expenseList,
            onDelete = { position ->
                expenseList.removeAt(position)
                adapter.notifyItemRemoved(position)
            },
            onItemClick = { selectedExpense ->
                val intent = Intent(this, ExpenseDetailsActivity::class.java).apply {
                    putExtra("expense_name", selectedExpense.name)
                    putExtra("expense_amount", selectedExpense.amount)
                    putExtra("expense_date", selectedExpense.date)
                }
                startActivity(intent)
            }
        )

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        openBrowserButton.setOnClickListener {
            val url = "https://www.financialtips.com"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }

        addButton.setOnClickListener {
            val name = nameInput.text.toString()
            val amount = amountInput.text.toString().toDoubleOrNull()
            val date = datePicker.text.toString()

            if (name.isEmpty() || amount == null || amount <= 0 || date.isEmpty()) {
                Toast.makeText(this, "Please enter all fields correctly", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            expenseList.add(Expense(name, amount, date))
            adapter.notifyItemInserted(expenseList.size - 1)
            nameInput.text?.clear()
            amountInput.text?.clear()
            datePicker.text?.clear()
            Toast.makeText(this, "Expense added", Toast.LENGTH_SHORT).show()
        }

        datePicker.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
                val selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                datePicker.setText(selectedDate)
            }, year, month, day).show()
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy called")
    }
}