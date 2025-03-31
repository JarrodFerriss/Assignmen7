package com.example.assignmen7.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.assignmen7.R

class HeaderFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_header, container, false)

        val headerTitle: TextView = view.findViewById(R.id.headerTitle)

        headerTitle.text = "Expenses"

        return view
    }
}
