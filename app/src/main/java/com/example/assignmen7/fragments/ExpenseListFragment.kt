package com.example.assignmen7.fragments

import android.app.DatePickerDialog
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.assignmen7.R
import com.example.assignmen7.adapter.ExpenseAdapter
import com.example.assignmen7.model.Expense
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.Calendar

class ExpenseListFragment : Fragment() {

    private val expenseList = mutableListOf<Expense>()
    private lateinit var adapter: ExpenseAdapter
    private val fileName = "expenses.json"
    private lateinit var totalAmountTextView: android.widget.TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_expense_list, container, false)

        totalAmountTextView = view.findViewById(R.id.totalAmountTextView)

        val nameInput = view.findViewById<TextInputEditText>(R.id.expenseName)
        val amountInput = view.findViewById<TextInputEditText>(R.id.expenseAmount)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        val addButton = view.findViewById<FloatingActionButton>(R.id.fab)
        val datePicker = view.findViewById<TextInputEditText>(R.id.expenseDate)
        val openBrowserButton = view.findViewById<Button>(R.id.openBrowserButton)

        adapter = ExpenseAdapter(
            expenseList,
            onDelete = { position -> deleteExpense(position) },
            onItemClick = { expense ->
                val action = ExpenseListFragmentDirections
                    .actionExpenseListFragmentToExpenseDetailsFragment(
                        expenseName = expense.name,
                        expenseAmount = expense.amount.toFloat(),
                        expenseDate = expense.date
                    )
                findNavController().navigate(action)
            }
        )

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        loadExpensesFromFile()

        openBrowserButton.setOnClickListener {
            val url = "https://www.financialtips.com"
            val intent = android.content.Intent(android.content.Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }

        addButton.setOnClickListener {
            val name = nameInput.text.toString()
            val amount = amountInput.text.toString().toDoubleOrNull()
            val date = datePicker.text.toString()

            if (name.isEmpty() || amount == null || amount <= 0 || date.isEmpty()) {
                Toast.makeText(requireContext(), "Please enter all fields correctly", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            expenseList.add(Expense(name, amount, date))
            adapter.notifyItemInserted(expenseList.size - 1)
            updateTotalAmount()
            saveExpensesToFile()
            nameInput.text?.clear()
            amountInput.text?.clear()
            datePicker.text?.clear()
            Toast.makeText(requireContext(), "Expense added", Toast.LENGTH_SHORT).show()
        }

        datePicker.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            DatePickerDialog(requireContext(), { _, selectedYear, selectedMonth, selectedDay ->
                val selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                datePicker.setText(selectedDate)
            }, year, month, day).show()
        }

        return view
    }

    private fun deleteExpense(position: Int) {
        expenseList.removeAt(position)
        adapter.notifyItemRemoved(position)
        saveExpensesToFile()
        updateTotalAmount()
    }

    private fun updateTotalAmount() {
        val total = expenseList.sumOf { it.amount }
        totalAmountTextView.text = "Total: $%.2f".format(total)
    }

    private fun saveExpensesToFile() {
        val json = Gson().toJson(expenseList)
        requireContext().openFileOutput(fileName, Context.MODE_PRIVATE).use {
            it.write(json.toByteArray())
        }
    }

    private fun loadExpensesFromFile() {
        try {
            val json = requireContext().openFileInput(fileName).bufferedReader().use { it.readText() }
            val type = object : TypeToken<MutableList<Expense>>() {}.type
            val loadedList: MutableList<Expense> = Gson().fromJson(json, type)
            expenseList.clear()
            expenseList.addAll(loadedList)
            adapter.notifyDataSetChanged()
            updateTotalAmount()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
