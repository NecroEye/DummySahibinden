package com.muratcangzm.dummysahibinden.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.muratcangzm.dummysahibinden.databinding.MainFragmentLayoutBinding
import com.muratcangzm.dummysahibinden.viewmodels.MainViewModel
import com.muratcangzm.network.mapper.VehicleType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlin.math.log

@AndroidEntryPoint
class MainFragment : Fragment() {

    private var _binding: MainFragmentLayoutBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = MainFragmentLayoutBinding.inflate(inflater, container, false)


        viewModel.fetchVehicleByType(VehicleType.carros)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewLifecycleOwner.lifecycleScope.launch {

            viewModel.vehicleData.collect {

                it?.let {

                    Log.d("Gelen Data: ", "${it.size}")

                } ?: Log.d("Gelen Data:", "Boş")

            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}