package com.challenge.currency.ui.main

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.challenge.currency.R
import com.challenge.currency.databinding.FragmentMainBinding
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : Fragment() {

  private val viewModel: MainViewModel by hiltNavGraphViewModels(R.id.converter_nav_graph)

  private lateinit var sharedPreferences: SharedPreferences

  private var _binding: FragmentMainBinding? = null
  private val binding get() = _binding!!

  private lateinit var spinnerAdapter: ArrayAdapter<String>

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    _binding = FragmentMainBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    checkSavedCurrencies()

    showProgress(false)
    initSpinners()
    listenToUiState()

    initViews()
  }

  private fun checkSavedCurrencies() {
    val currencies = loadCurrencies()
    if (currencies.isEmpty()) viewModel.fetchCurrencies()
    else viewModel.updateCurrencies(currencies)
  }

  private fun initViews() {
    binding.imgRefresh.setOnClickListener { viewModel.fetchCurrencies() }
    binding.imgSwitch.setOnClickListener {
      val currFrom = binding.spCurrFrom.selectedItemPosition
      val currTo = binding.spCurrTo.selectedItemPosition

      binding.spCurrFrom.setSelection(currTo, false)
      viewModel.updateCurrFrom(currTo)
      binding.spCurrTo.setSelection(currFrom, false)
      viewModel.updateCurrTo(currFrom)
    }

    binding.etCurrFrom.addTextChangedListener {
      if (it.isNullOrEmpty()) binding.etCurrFrom.setText("1.0")
      else {
        val currFrom = binding.spCurrFrom.selectedItem.toString()
        val currTo = binding.spCurrTo.selectedItem.toString()
        viewModel.fetchConversion(currTo, currFrom, it.toString().toDouble())
      }
    }

    binding.etCurrTo.addTextChangedListener {

    }
  }

  private fun initSpinners() {
    spinnerAdapter =
      ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_item, mutableListOf())
    spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

    with(binding.spCurrFrom) {
      adapter = spinnerAdapter
      setSelection(0, false)
      onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
          viewModel.updateCurrFrom(p2)
        }

        override fun onNothingSelected(p0: AdapterView<*>?) {
          //TODO("Not yet implemented")
        }
      }
      prompt = getString(R.string.select_currency)
      gravity = Gravity.CENTER
    }

    with(binding.spCurrTo) {
      adapter = spinnerAdapter
      setSelection(0, false)
      onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
          viewModel.updateCurrTo(p2)
        }

        override fun onNothingSelected(p0: AdapterView<*>?) {
          //TODO("Not yet implemented")
        }
      }
      prompt = getString(R.string.select_currency)
      gravity = Gravity.CENTER
    }
  }

  private fun listenToUiState() {
    lifecycleScope.launch {
      repeatOnLifecycle(Lifecycle.State.STARTED) {
        viewModel.uiState.collect { uiState ->
          // New value received
          when {
            uiState.isLoading -> showProgress(true)
            uiState.isError -> {
              showProgress(false)
              showError(getString(R.string.curr_error))
            }
            else -> {
              showProgress(false)
              if (uiState.currencies.isNotEmpty()) fillSpinners(uiState.currencies)
              if (uiState.isApi) saveCurrencies(uiState.currencies)
            }
          }
        }
      }
    }
  }

  private fun fillSpinners(currencies: Set<String>) {
    spinnerAdapter.clear()
    spinnerAdapter.addAll(currencies.toList().sorted())

    binding.spCurrFrom.setSelection(viewModel.uiState.value.currFromPos, false)
    binding.spCurrTo.setSelection(viewModel.uiState.value.currToPos, false)
  }

  private fun showProgress(show: Boolean) {
    if (show) binding.progressBar.visibility = View.VISIBLE
    else binding.progressBar.visibility = View.GONE
  }

  private fun showError(error: String) {
    Snackbar.make(binding.mainFragment, error, Snackbar.LENGTH_SHORT).show()
  }

  private fun saveCurrencies(currencies: Set<String>) {
    val editor = sharedPreferences.edit()
    editor.putStringSet("currencies_set", currencies)
    editor.apply()
  }

  private fun loadCurrencies(): Set<String> {
    sharedPreferences =
      requireContext().getSharedPreferences("currency_converter", Context.MODE_PRIVATE)
    return sharedPreferences.getStringSet("currencies_set", emptySet())!!.toSet()
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }
}