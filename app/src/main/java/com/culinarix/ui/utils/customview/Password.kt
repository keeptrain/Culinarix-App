package com.culinarix.ui.utils.customview

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import com.culinarix.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class Password : TextInputEditText {

    private var passwordLayout: TextInputLayout? = null

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
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString().length < 8 ) {
                    passwordLayout?.error = context.getString(R.string.passwordError)
                    passwordLayout?.errorIconDrawable = null
                } else {
                    passwordLayout?.error = null
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })

    }

    fun setPasswordLayout(layout: TextInputLayout) {
        passwordLayout = layout
    }
}