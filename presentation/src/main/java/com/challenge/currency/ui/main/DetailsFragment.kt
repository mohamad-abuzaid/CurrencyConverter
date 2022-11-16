package com.challenge.currency.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.challenge.currency.R
import com.challenge.currency.databinding.FragmentDetailsBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailsFragment : Fragment() {

  private val viewModel: MainViewModel by hiltNavGraphViewModels(R.id.converter_nav_graph)

  private var _binding: FragmentDetailsBinding? = null
  private val binding get() = _binding!!

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


    showProgress(false)
    listenToUiState()

    initListeners()
  }

  private fun initListeners() {

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
    Snackbar.make(binding.mainFragment, error, Snackbar.LENGTH_SHORT).show()
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }
}