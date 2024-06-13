package com.muratcangzm.dummysahibinden.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.muratcangzm.dummysahibinden.databinding.ExtendedFragmentLayoutBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExtendedFragment: Fragment() {

    private var _binding:ExtendedFragmentLayoutBinding? = null
    private val binding get() = _binding!!
    private var receivedData:Int? = null

    private val args: ExtendedFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = ExtendedFragmentLayoutBinding.inflate(inflater, container, false)

        Log.d("Fragment Verisi", "${args.vehicleArg}")
        receivedData = args.vehicleArg

        Toast.makeText(requireContext(), "$receivedData", Toast.LENGTH_SHORT).show()

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null

    }




}