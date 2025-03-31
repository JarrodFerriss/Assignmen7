package com.example.assignmen7

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.assignmen7.adapter.ExpenseAdapter
import com.example.assignmen7.fragments.FooterFragment
import com.example.assignmen7.fragments.HeaderFragment
import com.example.assignmen7.model.Expense
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.Calendar

class MainActivity : AppCompatActivity() {

    private val expenseList = mutableListOf<Expense>()
    private lateinit var adapter: ExpenseAdapter
    private lateinit var footerFragment: FooterFragment

    companion object {
        private const val TAG = "MainActivity"
    }

    private val fileName = "expenses.json"

    private fun saveExpensesToFile() {
        val json = Gson().toJson(expenseList)
        openFileOutput(fileName, Context.MODE_PRIVATE).use {
            it.write(json.toByteArray())
        }
    }

    private fun loadExpensesFromFile() {
        try {
            val json = openFileInput(fileName).bufferedReader().use { it.readText() }
            val type = object : TypeToken<MutableList<Expense>>() {}.type
            val loadedList: MutableList<Expense> = Gson().fromJson(json, type)
            expenseList.clear()
            expenseList.addAll(loadedList)
            adapter.notifyDataSetChanged()
            updateFooter()
        } catch (e: Exception) {
            Log.e(TAG, "Error loading expenses: ${e.message}")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG, "onCreate called")

        val nameInput = findViewById<TextInputEditText>(R.id.expenseName)
        val amountInput = findViewById<TextInputEditText>(R.id.expenseAmount)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val addButton = findViewById<FloatingActionButton>(R.id.fab)
        val datePicker = findViewById<TextInputEditText>(R.id.expenseDate)
        val openBrowserButton = findViewById<Button>(R.id.openBrowserButton)

        replaceFragment(HeaderFragment(), R.id.headerContainer)
        footerFragment = FooterFragment()
        replaceFragment(footerFragment, R.id.footerContainer)

        adapter = ExpenseAdapter(
            expenseList,
            onDelete = { position ->
                val removedExpense = expenseList[position]
                expenseList.removeAt(position)
                adapter.notifyItemRemoved(position)
                updateFooter()
                saveExpensesToFile()

                Snackbar.make(recyclerView, "Expense deleted", Snackbar.LENGTH_LONG)
                    .setAction("Undo") {
                        expenseList.add(position, removedExpense)
                        adapter.notifyItemInserted(position)
                        updateFooter()
                        saveExpensesToFile()
                    }.show()
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

        loadExpensesFromFile()

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
            saveExpensesToFile()
            nameInput.text?.clear()
            amountInput.text?.clear()
            datePicker.text?.clear()
            Toast.makeText(this, "Expense added", Toast.LENGTH_SHORT).show()
            updateFooter()
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

    private fun replaceFragment(fragment: Fragment, containerId: Int) {
        supportFragmentManager.beginTransaction()
            .replace(containerId, fragment)
            .commit()
    }

    private fun updateFooter() {
        val total = expenseList.sumOf { it.amount }
        footerFragment.updateTotalAmount(total)
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
