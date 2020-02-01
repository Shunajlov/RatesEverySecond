package com.ihavenodomain.rateseverysecond.ui.main

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import com.ihavenodomain.rateseverysecond.R
import com.ihavenodomain.rateseverysecond.ui.main.list.CurrenciesListAdapter
import kotlinx.android.synthetic.main.main_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : Fragment() {
    private val viewModel: MainViewModel by viewModel()
    private lateinit var adapter: CurrenciesListAdapter

    /**
     * used for smooth scrolling to the top of the list
     * @see smoothScrollToTop()
     */
    private lateinit var smoothScroller: RecyclerView.SmoothScroller

    var baseUpdated = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        smoothScroller = object : LinearSmoothScroller(context) {
            override fun getVerticalSnapPreference(): Int {
                return SNAP_TO_START
            }
        }

        initView()

        startObservingData()
    }

    private fun initView() {
        val clicker: CurrenciesListAdapter.ItemClickListener = object : CurrenciesListAdapter.ItemClickListener {
            override fun onItemClick(position: Int) {
                viewModel.updateBaseCurrency(position)
                baseUpdated = true
            }
        }

        val textWatcher: TextWatcher = object : TextWatcher {
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                if (p0 == null) return
                val multiplier = if (p0.toString().isBlank()) 0.0 else p0.toString().toDouble()
                viewModel.updateMultiplier(multiplier)
            }
        }

        adapter = CurrenciesListAdapter(clicker, textWatcher)
        vItems.layoutManager = LinearLayoutManager(context)
        vItems.adapter = adapter
    }

    private fun startObservingData() {
        viewModel.observeCurrencyInfo().observe(viewLifecycleOwner, Observer {
            if (it.isNotEmpty()) {
                vItems.visibility = View.VISIBLE
                vError.visibility = View.GONE

                adapter.submitList(it)

                if (baseUpdated) {
                    smoothScrollToTop()
                    baseUpdated = false
                }
            } else {
                vItems.visibility = View.GONE
                vError.visibility = View.VISIBLE
            }
        })
    }

    private fun smoothScrollToTop() {
        smoothScroller.targetPosition = 0
        vItems.layoutManager?.startSmoothScroll(smoothScroller)
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}
