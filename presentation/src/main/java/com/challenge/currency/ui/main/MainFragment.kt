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
import com.challenge.currency.R
import com.challenge.currency.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

  companion object {
    fun newInstance() = MainFragment()
  }

  private val viewModel: MainViewModel by hiltNavGraphViewModels(R.id.converter_nav_graph)

  private var _binding: FragmentMainBinding? = null
  private val binding get() = _binding!!

  val FROM_SPINNER_ID = 1
  val TO_SPINNER_ID = 2

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    _binding = FragmentMainBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    initSpinners()
  }

  private fun initSpinners() {
    val aa =
      ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_item, emptyList())
    aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

    with(binding.spCurrFrom) {
      adapter = aa
      setSelection(0, false)
      onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
          TODO("Not yet implemented")
        }

        override fun onNothingSelected(p0: AdapterView<*>?) {
          TODO("Not yet implemented")
        }
      }
      prompt = "Select your favourite language"
      gravity = Gravity.CENTER
    }
    binding.spCurrFrom.id = FROM_SPINNER_ID

    with(binding.spCurrTo) {
      adapter = aa
      setSelection(0, false)
      onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
          TODO("Not yet implemented")
        }

        override fun onNothingSelected(p0: AdapterView<*>?) {
          TODO("Not yet implemented")
        }
      }
      prompt = "Select your favourite language"
      gravity = Gravity.CENTER
    }
    binding.spCurrTo.id = TO_SPINNER_ID

  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }
}