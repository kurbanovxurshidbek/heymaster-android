package com.heymaster.heymaster.utils.extensions

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

fun EditText.enterPhoneNumber(){
    this.addTextChangedListener (
        object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {

                Log.d("@@@", p0.toString())
                for (i in 2 until p0.toString().length step 2) {
                    if (p0.toString()[i] !=  ' ') {
                        p0?.insert(i, " ")
                    }
                }
            }
        }
    )

}