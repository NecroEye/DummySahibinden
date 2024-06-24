package com.muratcangzm.dummysahibinden.ui.fragments

import android.app.Dialog
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.core.view.isNotEmpty
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.Visibility
import com.google.android.material.button.MaterialButton
import com.muratcangzm.dummysahibinden.R
import com.muratcangzm.dummysahibinden.common.listener.NetworkListener
import com.muratcangzm.dummysahibinden.common.navigation.FragmentNavigator
import com.muratcangzm.dummysahibinden.databinding.MainFragmentLayoutBinding
import com.muratcangzm.dummysahibinden.ui.adapters.MainAdapter
import com.muratcangzm.dummysahibinden.ui.fragments.core.BaseFragment
import com.muratcangzm.dummysahibinden.utils.NetworkConnection
import com.muratcangzm.dummysahibinden.viewmodels.MainViewModel
import com.muratcangzm.dummysahibinden.viewmodels.core.ViewModelFactory
import com.muratcangzm.network.mapper.VehicleType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : BaseFragment<MainFragmentLayoutBinding>() {

    override val layoutId: Int
        get() = R.layout.main_fragment_layout

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var mainAdapter: MainAdapter

    private val viewModel: MainViewModel by viewModels { viewModelFactory }

    private var vehicleType: VehicleType? = null
    private val fragmentNavigator: FragmentNavigator by lazy { FragmentNavigator(this) }

    private lateinit var networkChangeReceiver: NetworkListener
    private lateinit var networkDialog: Dialog
    private lateinit var vehicleDialog: Dialog

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): MainFragmentLayoutBinding {

        return MainFragmentLayoutBinding.inflate(inflater, container, false)
    }

    override fun MainFragmentLayoutBinding.initializeViews() {
        //Not necessary in this fragment rn
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainAdapter.setFragmentNavigation(fragmentNavigator)
        setAdapter()

        viewModel.fetchVehicleYearList(VehicleType.carros, 59, 5940)
        viewModel.fetchVehicleDetails(VehicleType.carros, 59, 5940, "2014-3")


        viewLifecycleOwner.lifecycleScope.launch {

            launch {
                viewModel.vehicleData.collectLatest { list ->

                    list?.let {

                        vehicleType?.let {
                            binding.loadingLayout.realLayout.visibility = View.GONE
                            binding.mainRecyclerView.visibility = View.VISIBLE
                            mainAdapter.submitCarMarcasModel(
                                list,
                                vehicleType!!,
                                this@MainFragment
                            )
                        }
                        Timber.tag("Gelen Data: ").d("${it.size}")

                    } ?: Timber.tag("Gelen Data:").d("Boş")

                }
            }


            launch {
                viewModel.vehicleYearListData.collectLatest {
                    it?.let {
                        Timber.tag("Gelen Year Data: ").d("${it.size}")
                    } ?: Timber.tag("Gelen Year Data:").d("Boş")
                }
            }

            launch {
                viewModel.vehicleDetails.collectLatest {
                    it?.let {
                        Timber.tag("Gelen Detail Data: ").d(it.brand)
                    } ?: Timber.tag("Gelen Detail Data: ").d("Boş")
                }
            }

        }
    }

    private fun showVehicleDialog() {

        if (!::vehicleDialog.isInitialized || !vehicleDialog.isShowing) {
            if (mainAdapter.mutableCarsModel.isEmpty()) {

                vehicleDialog = Dialog(requireContext())
                vehicleDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                vehicleDialog.setCancelable(false)
                vehicleDialog.setContentView(R.layout.custom_dialog_layout)
                vehicleDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))


                val carsButton: MaterialButton = vehicleDialog.findViewById(R.id.carsButton)
                val motorButton: MaterialButton = vehicleDialog.findViewById(R.id.motosButton)
                val truckButton: MaterialButton = vehicleDialog.findViewById(R.id.caminhoesButton)

                carsButton.setOnClickListener {

                    vehicleType = VehicleType.carros
                    viewModel.fetchVehicleByType(VehicleType.carros)


                    vehicleDialog.dismiss()
                }
                motorButton.setOnClickListener {

                    vehicleType = VehicleType.motos
                    viewModel.fetchVehicleByType(VehicleType.motos)

                    vehicleDialog.dismiss()
                }
                truckButton.setOnClickListener {

                    vehicleType = VehicleType.caminhoes
                    viewModel.fetchVehicleByType(VehicleType.caminhoes)

                    vehicleDialog.dismiss()
                }

                vehicleDialog.show()

            }

        }
    }

    private fun showNetworkDialog() {

        if (!::networkDialog.isInitialized || !networkDialog.isShowing) {
            networkDialog = Dialog(requireContext())
            networkDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            networkDialog.setCancelable(false)
            networkDialog.setContentView(R.layout.internet_listener_dialog)
            networkDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            val retryButton: MaterialButton = networkDialog.findViewById(R.id.reconnectButton)

            retryButton.setOnClickListener {
                if (NetworkConnection.isNetworkAvailable(requireContext())) {
                    showVehicleDialog()
                    networkDialog.dismiss()
                }
            }
            networkDialog.show()
        }
    }

    private fun setAdapter() {

        binding.mainRecyclerView.apply {

            adapter = mainAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            hasFixedSize()

        }
    }

    override fun onStart() {
        super.onStart()

        networkChangeReceiver = NetworkListener { isConnected ->

            if (isConnected) {
                showVehicleDialog()
            } else {
                showNetworkDialog()
            }
        }

        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        requireContext().registerReceiver(networkChangeReceiver, filter)
    }

    override fun onStop() {
        super.onStop()
        requireContext().unregisterReceiver(networkChangeReceiver)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        if (::vehicleDialog.isInitialized && vehicleDialog.isShowing)
            vehicleDialog.dismiss()

        if (::networkDialog.isInitialized && networkDialog.isShowing)
            vehicleDialog.dismiss()


    }

}
