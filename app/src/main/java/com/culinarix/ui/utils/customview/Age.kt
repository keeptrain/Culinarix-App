package com.culinarix.ui.utils.customview

import android.content.Context
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Patterns
import com.culinarix.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class Age : TextInputEditText {

    private var ageLayout: TextInputLayout? = null
    private var isValid: Boolean = false

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
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                isValid = s?.isNotEmpty() == true && s.length >= 2

                if (!isValid) {
                    ageLayout?.error = context.getString(R.string.ageError)
                } else {
                    ageLayout?.error = null
                }
            }

            override fun afterTextChanged(p0: Editable?) {}
        })
    }

    fun isValid(): Boolean {
        return isValid
    }

    fun setAgeLayout(layout: TextInputLayout) {
        ageLayout = layout
    }
}
