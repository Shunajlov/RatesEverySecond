package com.ihavenodomain.rateseverysecond.ui.main.list

import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ihavenodomain.rateseverysecond.R
import java.math.RoundingMode
import java.text.DecimalFormat
import java.util.*

class CurrencyViewHolder(parent: ViewGroup): RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.item_currency, parent, false)
) {

    private val vIcon: ImageView = itemView.findViewById(R.id.vIcon)
    private val vAbbreviation: TextView = itemView.findViewById(R.id.vAbbreviation)
    private val vName: TextView = itemView.findViewById(R.id.vName)
    private val vCurrencyEdit: EditText = itemView.findViewById(R.id.vCurrencyEdit)

    private val resources = itemView.context.resources
    private val packageName = itemView.context.packageName

    fun bindNameAndIcon(name: String) {
        setName(name)
        setIcon(name)
    }

    fun setValueText(text: String = "") {
        vCurrencyEdit.setText(text)
        vCurrencyEdit.setSelection(text.length)
    }

    fun setValue(value: Double) {
        val df = DecimalFormat("#.####")
        df.roundingMode = RoundingMode.FLOOR
        val newValue = df.format(value)
        vCurrencyEdit.setText(newValue)
        vCurrencyEdit.setSelection(newValue.length)
    }

    fun addTextWatcher(watcher: TextWatcher) {
        vCurrencyEdit.addTextChangedListener(watcher)
    }

    fun removeTextWatcher(watcher: TextWatcher) {
        vCurrencyEdit.removeTextChangedListener(watcher)
    }

    private fun setIcon(name: String) {
        val lowerName = name.toLowerCase(Locale.getDefault())
        val drwId = resources.getIdentifier(lowerName, "drawable", packageName)
        if (drwId != 0) {
            vIcon.setImageResource(drwId)
        } else {
            vIcon.setImageResource(R.drawable.unknown)
        }
    }

    private fun setName(name: String) {
        vAbbreviation.text = name
        val strId = resources.getIdentifier(name, "string", packageName)
        if (strId != 0) vName.setText(strId)
    }
}