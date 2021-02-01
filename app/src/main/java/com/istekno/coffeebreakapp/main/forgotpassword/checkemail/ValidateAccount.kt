package com.istekno.coffeebreakapp.main.forgotpassword.checkemail

import android.widget.EditText
import com.google.android.material.textfield.TextInputLayout
import com.istekno.coffeebreakapp.main.forgotpassword.checkemail.Utils.Companion.isValidEmail

class ValidateAccount {
    companion object {

        fun valEmail(inEmail: TextInputLayout, etEmail: EditText): Boolean {
            val text = etEmail.text.toString().trim()

            if (!isValidEmail(text)) {
                inEmail.isHelperTextEnabled = true
                inEmail.helperText = "Please enter valid email!"
                etEmail.requestFocus()

                return false
            } else {
                inEmail.isHelperTextEnabled = false
            }

            return true
        }

        fun valPassword(inPass: TextInputLayout, etPass: EditText): Boolean {
            val text = etPass.text.toString().trim()

            when {
                text.isEmpty() -> {
                    inPass.isHelperTextEnabled = true
                    inPass.helperText = "Please enter your password!"
                    etPass.requestFocus()

                    return false
                }
                text.length < 8 -> {
                    inPass.isHelperTextEnabled = true
                    inPass.helperText = "Min. 8 character!"
                    etPass.requestFocus()

                    return false
                }
                else -> {
                    inPass.isHelperTextEnabled = false
                }
            }

            return true
        }

        fun valPassConf(inPassConf: TextInputLayout, etPassConf: EditText, etPass: EditText): Boolean {
            val textConf = etPassConf.text.toString().trim()
            val text = etPass.text.toString().trim()

            when {
                textConf.isEmpty() -> {
                    inPassConf.isHelperTextEnabled = true
                    inPassConf.helperText = "Please enter your confirmation password!"
                    etPassConf.requestFocus()

                    return false
                }
                textConf != text -> {
                    inPassConf.isHelperTextEnabled = true
                    inPassConf.helperText = "Confirmation password not matches!"
                    etPassConf.requestFocus()

                    return false
                }
                else -> {
                    inPassConf.isHelperTextEnabled = false
                }
            }

            return true
        }
    }
}