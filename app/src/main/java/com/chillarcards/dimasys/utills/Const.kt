package com.chillarcards.dimasys.utills

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import com.chillarcards.dimasys.R

class Const {
    companion object {


        const val ver_title = ":  " //Client
        val emailRegex = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})".toRegex()

        fun enableButton(button: Button) {
            button.isEnabled = true
            button.alpha = 1f
        }

        fun disableButton(button: Button) {
            button.isEnabled = false
            button.alpha = 0.55f
        }

        fun shortToast(context: Context, value: String) {
            //Toast.makeText(context, value, Toast.LENGTH_SHORT).show()
            val builder = AlertDialog.Builder(context)
            //set message for alert dialog
            builder.setMessage(value)
            //performing negative action
            builder.setNegativeButton(context.getString(R.string.close)) { dialogInterface, _ ->
                dialogInterface.dismiss()
            }
            // Create the AlertDialog
            val alertDialog: AlertDialog = builder.create()
            // Set other dialog properties
            alertDialog.setOnShowListener {
                alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(context.resources.getColor(R.color.primary_red)
                )
            }
            alertDialog.setCanceledOnTouchOutside(false)
            alertDialog.show()
        }

        fun hideSoftKey(requireContext: Context, view: View) {
            val imm = requireContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
        fun maskPhoneNumber(phoneNumber: String): String {
            if (phoneNumber.length < 5) {
                return phoneNumber
            }
            val maskedLength = phoneNumber.length - 5
            val maskedString =
                "*".repeat(maskedLength)
            return maskedString + phoneNumber.substring(phoneNumber.length - 5)
        }
        fun maskEmailAddress(email: String): String {
            val regex = """(?:\G(?!^)|(?<=^[^@]{2}|@))[^@](?!\.[^.]+$)""".toRegex()
            val emailMask = email.replace(regex, "*")
            return emailMask
        }
    }
}