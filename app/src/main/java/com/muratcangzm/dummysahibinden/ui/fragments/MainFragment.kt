package com.muratcangzm.dummysahibinden.ui.fragments

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.button.MaterialButton
import com.muratcangzm.dummysahibinden.R
import com.muratcangzm.dummysahibinden.databinding.MainFragmentLayoutBinding
import com.muratcangzm.dummysahibinden.ui.adapters.MainAdapter
import com.muratcangzm.dummysahibinden.viewmodels.MainViewModel
import com.muratcangzm.network.mapper.VehicleType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.log

@AndroidEntryPoint
class MainFragment : Fragment() {

    private var _binding: MainFragmentLayoutBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by viewModels()

    @Inject
    lateinit var mainAdapter: MainAdapter

    private var vehicleType: VehicleType? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = MainFragmentLayoutBinding.inflate(inflater, container, false)

        showCustomDialog()
        setAdapter()

        viewModel.fetchVehicleByBrandCode(type = VehicleType.carros, codigo = 59)
        viewModel.fetchVehicleYearList(VehicleType.carros, 59, 5940)
        viewModel.fetchVehicleDetails(VehicleType.carros, 59, 5940, "2014-3")

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewLifecycleOwner.lifecycleScope.launch {

            launch {
                viewModel.vehicleData.collect { list ->

                    list?.let {

                        vehicleType?.let {

                            mainAdapter.submitCarMarcasModel(
                                list,
                                vehicleType!!,
                                this@MainFragment
                            )
                        }
                        Log.d("Gelen Data: ", "${it.size}")

                    } ?: Log.d("Gelen Data:", "Boş")

                }
            }


            launch {
                viewModel.brandCodeData.collect {

                    it?.let {
                        Log.d("Gelen Brand Data: ", "${it.models.size}")
                    } ?: Log.d("Gelen Brand Data:", "Boş")

                }
            }


            launch {
                viewModel.vehicleYearListData.collect {
                    it?.let {
                        Log.d("Gelen Year Data: ", "${it.size}")
                    } ?: Log.d("Gelen Year Data:", "Boş")
                }
            }

            launch {
                viewModel.vehicleDetails.collect {
                    it?.let {
                        Log.d("Gelen Detail Data: ", it.brand)
                    } ?: Log.d("Gelen Detail Data: ", "Boş")
                }
            }

        }

    }

    private fun showCustomDialog() {


        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.custom_dialog_layout)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))


        val carsButton: MaterialButton = dialog.findViewById(R.id.carsButton)
        val motorButton: MaterialButton = dialog.findViewById(R.id.motosButton)
        val truckButton: MaterialButton = dialog.findViewById(R.id.caminhoesButton)

        carsButton.setOnClickListener {

            vehicleType = VehicleType.carros
            viewModel.fetchVehicleByType(VehicleType.carros)


            dialog.dismiss()
        }
        motorButton.setOnClickListener {

            vehicleType = VehicleType.motos
            viewModel.fetchVehicleByType(VehicleType.motos)

            dialog.dismiss()
        }
        truckButton.setOnClickListener {

            vehicleType = VehicleType.caminhoes
            viewModel.fetchVehicleByType(VehicleType.caminhoes)

            dialog.dismiss()
        }

        dialog.show()

    }

    private fun setAdapter() {

        binding.mainRecyclerView.apply {

            adapter = mainAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            hasFixedSize()

        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
