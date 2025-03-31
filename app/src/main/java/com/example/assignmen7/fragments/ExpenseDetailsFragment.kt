package com.example.assignmen7.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.assignmen7.R

class ExpenseDetailsFragment : Fragment() {
    private val args: ExpenseDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_expense_details, container, false)

        view.findViewById<TextView>(R.id.expenseNameTextView).text = "Name: ${args.expenseName}"
        view.findViewById<TextView>(R.id.expenseAmountTextView).text = "Amount: $%.2f".format(args.expenseAmount)
        view.findViewById<TextView>(R.id.expenseDateTextView).text = "Date: ${args.expenseDate}"

        view.findViewById<Button>(R.id.backToHomeButton).setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        return view
    }
}
