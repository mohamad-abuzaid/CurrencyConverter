package com.challenge.currency.ui.main

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.challenge.currency.R
import com.challenge.currency.databinding.FragmentMainBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : Fragment() {

  private val viewModel: MainViewModel by hiltNavGraphViewModels(R.id.converter_nav_graph)

  private var _binding: FragmentMainBinding? = null
  private val binding get() = _binding!!

  private lateinit var spinnerAdapter: ArrayAdapter<String>
  private val FROM_SPINNER_ID = 1
  private val TO_SPINNER_ID = 2

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    _binding = FragmentMainBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    showProgress(false)
    initSpinners()
    listenToUiState()
  }

  private fun initSpinners() {
    spinnerAdapter =
      ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_item)
    spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

    with(binding.spCurrFrom) {
      adapter = spinnerAdapter
      setSelection(0, false)
      onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
          TODO("Not yet implemented")
        }

        override fun onNothingSelected(p0: AdapterView<*>?) {
          TODO("Not yet implemented")
        }
      }
      prompt = getString(R.string.select_currency)
      gravity = Gravity.CENTER
    }
    binding.spCurrFrom.id = FROM_SPINNER_ID

    with(binding.spCurrTo) {
      adapter = spinnerAdapter
      setSelection(0, false)
      onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
          TODO("Not yet implemented")
        }

        override fun onNothingSelected(p0: AdapterView<*>?) {
          TODO("Not yet implemented")
        }
      }
      prompt = getString(R.string.select_currency)
      gravity = Gravity.CENTER
    }
    binding.spCurrTo.id = TO_SPINNER_ID

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
              fillSpinners(uiState.currencies)
            }
          }
        }
      }
    }
  }

  private fun fillSpinners(currencies: Map<String, String>) {
    spinnerAdapter.clear()
    spinnerAdapter.addAll(currencies.keys)
  }

  private fun showProgress(show: Boolean) {
    if (show) binding.progressBar.visibility = View.VISIBLE
    else binding.progressBar.visibility = View.GONE
  }

  private fun showError(error: String) {
    Snackbar.make(binding.mainFragment, error, Snackbar.LENGTH_SHORT).show()
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }
}