package com.challenge.currency.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.challenge.currency.R
import com.challenge.currency.databinding.FragmentDetailsBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class DetailsFragment : Fragment() {

  private val viewModel: MainViewModel by hiltNavGraphViewModels(R.id.converter_nav_graph)
  private val args: DetailsFragmentArgs by navArgs()

  private lateinit var currFrom: String
  private lateinit var currTo: String

  private var _binding: FragmentDetailsBinding? = null
  private val binding get() = _binding!!

  private val currenciesList =
    mutableListOf("USD", "AED", "EGP", "GBP", "CAD", "EUR", "CHF", "AUD", "KWD", "QAR")

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    //return inflater.inflate(R.layout.fragment_main, container, false)

    _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_details, container, false)
    binding.lifecycleOwner = this
    binding.vm = viewModel
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    currFrom = args.currFrom
    currTo = args.currTo
    currenciesList.add(0, currTo)

    requireActivity().onBackPressedDispatcher
      .addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
          findNavController().popBackStack()
        }
      })

    showProgress(false)
    initListeners()
    listenToUiState()

    fetchHistory()
  }

  private fun initListeners() {
    binding.imgRefresh.setOnClickListener { fetchHistory() }
  }

  private fun fetchHistory() {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val startDate = formatter.format(LocalDate.now())
    val endDate = formatter.format(LocalDate.now().plusDays(2))
    viewModel.fetchHistoryRates(
      startDate,
      endDate,
      currFrom,
      currenciesList.joinToString(",")
    )
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
            }
          }
        }
      }
    }
  }

  private fun showProgress(show: Boolean) {
    if (show) binding.progressBar.visibility = View.VISIBLE
    else binding.progressBar.visibility = View.GONE
  }

  private fun showError(error: String) {
    Snackbar.make(binding.detailsFragment, error, Snackbar.LENGTH_SHORT).show()
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }
}