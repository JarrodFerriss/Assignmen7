package com.example.assignmen7.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.assignmen7.MainActivity
import com.example.assignmen7.R

class ExpenseDetailsFragment : Fragment() {

    companion object {
        fun newInstance(name: String, amount: Double, date: String): ExpenseDetailsFragment {
            val fragment = ExpenseDetailsFragment()
            val args = Bundle()
            args.putString("expense_name", name)
            args.putDouble("expense_amount", amount)
            args.putString("expense_date", date)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_expense_details, container, false)

        val name = arguments?.getString("expense_name") ?: "Unknown"
        val amount = arguments?.getDouble("expense_amount") ?: 0.0
        val date = arguments?.getString("expense_date") ?: "No Date"

        view.findViewById<TextView>(R.id.expenseNameTextView).text = "Name: $name"
        view.findViewById<TextView>(R.id.expenseAmountTextView).text = "Amount: $%.2f".format(amount)
        view.findViewById<TextView>(R.id.expenseDateTextView).text = "Date: $date"

        view.findViewById<Button>(R.id.backToHomeButton).setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        return view
    }
}
