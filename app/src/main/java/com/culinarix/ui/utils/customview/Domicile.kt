package com.culinarix.ui.utils.customview

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.widget.ArrayAdapter
import com.culinarix.R
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.google.android.material.textfield.TextInputLayout

class Domicile : MaterialAutoCompleteTextView {

    private var domicileLayout: TextInputLayout? = null
    private val validCities = setOf("Ambon", "Bandung", "Jakarta", "Jambi", "Jember")

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        val adapter = ArrayAdapter(context, android.R.layout.simple_list_item_1, validCities.toTypedArray())
        setAdapter(adapter)

        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s != null) {
                    validateDomicile(s.toString())
                }
            }

            override fun afterTextChanged(p0: Editable?) {}
        })
    }

    private fun validateDomicile(input: String) {
        val isValid = validCities.contains(input.trim())
        if (!isValid) {
            domicileLayout?.error = context.getString(R.string.domicilieError)
        } else {
            domicileLayout?.error = null
        }
    }

    fun isValid(): Boolean {
        return domicileLayout?.error == null
    }

    fun setDomicileLayout(layout: TextInputLayout) {
        domicileLayout = layout
    }
}
