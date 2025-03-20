package com.example.assignmen7.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.assignmen7.R
import com.example.assignmen7.model.Expense

class ExpenseAdapter(
    private val expenses: MutableList<Expense>,
    private val onDelete: (Int) -> Unit,
    private val onItemClick: (Expense) -> Unit
) : RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder>() {

    inner class ExpenseViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val nameTextView: TextView = view.findViewById(R.id.expenseNameTextView)
        private val amountTextView: TextView = view.findViewById(R.id.expenseAmountTextView)
        private val deleteButton: Button = view.findViewById(R.id.deleteButton)

        fun bind(expense: Expense) {
            nameTextView.text = expense.name
            amountTextView.text = "$%.2f".format(expense.amount)

            // Handle delete button click
            deleteButton.setOnClickListener { onDelete(adapterPosition) }

            // Handle item click to open details
            itemView.setOnClickListener { onItemClick(expense) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_expense, parent, false)
        return ExpenseViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        holder.bind(expenses[position])
    }

    override fun getItemCount(): Int = expenses.size
}
