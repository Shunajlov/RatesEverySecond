package com.ihavenodomain.rateseverysecond.ui.main.list

import android.text.TextWatcher
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.ihavenodomain.rateseverysecond.model.CurrencyPayload
import com.ihavenodomain.rateseverysecond.model.CurrencyRate

private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<CurrencyRate>() {
    override fun areContentsTheSame(oldItem: CurrencyRate, newItem: CurrencyRate): Boolean {
        return oldItem == newItem
    }

    override fun areItemsTheSame(oldItem: CurrencyRate, newItem: CurrencyRate): Boolean {
        return oldItem.name == newItem.name
    }

    override fun getChangePayload(oldItem: CurrencyRate, newItem: CurrencyRate): Any? {
        return newItem.calculatePayload(oldItem)
    }
}

open class CurrenciesListAdapter(
    private val itemClicker: ItemClickListener,
    private val textWatcher: TextWatcher
) : ListAdapter<CurrencyRate, CurrencyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val holder = CurrencyViewHolder(parent)

        if (viewType == TYPE_BASE) {
            holder.addTextWatcher(textWatcher)
        } else {
            holder.itemView.setOnClickListener {
                itemClicker.onItemClick(holder.adapterPosition)
            }
            holder.removeTextWatcher(textWatcher)
        }

        return holder
    }

    override fun onBindViewHolder(
        holder: CurrencyViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position)
            return
        }

        payloads.filterIsInstance<CurrencyPayload>().firstOrNull {
            it.value != null || it.name != null
        }?.let {
            if (it.name != null) holder.bindNameAndIcon(it.name)
            if (it.value != null) holder.setValue(it.value)
        }
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        holder.bindNameAndIcon(getItem(position).name)
        holder.setValue(getItem(position).value)
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) TYPE_BASE else TYPE_GENERIC
    }

    companion object {
        private const val TYPE_BASE = 0
        private const val TYPE_GENERIC = 1
    }

    interface ItemClickListener {
        fun onItemClick(position: Int)
    }
}