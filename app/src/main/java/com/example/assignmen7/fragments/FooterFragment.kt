package com.example.assignmen7.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.assignmen7.R

class FooterFragment : Fragment() {

    private lateinit var totalAmountTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_footer, container, false)
        totalAmountTextView = view.findViewById(R.id.totalAmountTextView)
        return view
    }

    fun updateTotalAmount(total: Double) {
        totalAmountTextView.text = "Total Expenses: $%.2f".format(total)
    }
}
