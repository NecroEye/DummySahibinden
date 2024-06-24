package com.muratcangzm.dummysahibinden.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.muratcangzm.dummysahibinden.common.navigation.FragmentNavigator
import com.muratcangzm.dummysahibinden.databinding.ExtendedFragmentLayoutBinding
import com.muratcangzm.dummysahibinden.ui.adapters.ExtendedAdapter
import com.muratcangzm.dummysahibinden.viewmodels.ExtendedViewModel
import com.muratcangzm.network.mapper.VehicleType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class ExtendedFragment : Fragment() {

    private var _binding: ExtendedFragmentLayoutBinding? = null
    private val binding get() = _binding!!
    private var type: VehicleType? = null
    private var id: Int? = null

    @Inject
    lateinit var extendedAdapter: ExtendedAdapter

    private val fragmentNavigator: FragmentNavigator by lazy { FragmentNavigator(this) }
    private val viewModel: ExtendedViewModel by viewModels()

    //private val args:ExtendedFragmentArgs by navArgs() doesn't work right now fix it later

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = ExtendedFragmentLayoutBinding.inflate(inflater, container, false)

        extendedAdapter.setFragmentNavigation(fragmentNavigator)

        arguments?.let { args ->
            type = args.getSerializable("vehicleType") as VehicleType
            id = args.getInt("id")
        }

        viewModel.fetchVehicleByBrandCode(type = type!!, codigo = id!!)


        Timber.d("receivedData: $type")
        Timber.d("receivedData: $id")


        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setAdapter()

        lifecycleScope.launch {

            viewModel.brandCodeData.collect {

                it?.let {

                    binding.extendedLoadingLayout.realLayout.visibility = View.GONE
                    binding.extendedRecyclerView.visibility = View.VISIBLE
                    binding.listTitle.visibility = View.VISIBLE

                    Timber.d("ikinci fragment ${it.models.size}")
                    extendedAdapter.setList(it, type!!, id!!)

                }
            }
        }

    }

    private fun setAdapter() {

        binding.apply {
            extendedRecyclerView.adapter = extendedAdapter
            extendedRecyclerView.hasFixedSize()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null

    }


}