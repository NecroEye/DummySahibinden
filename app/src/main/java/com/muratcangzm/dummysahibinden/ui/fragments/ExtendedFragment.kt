package com.muratcangzm.dummysahibinden.ui.fragments

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.annotation.LayoutRes
import androidx.annotation.VisibleForTesting
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.google.android.material.button.MaterialButton
import com.muratcangzm.dummysahibinden.R
import com.muratcangzm.dummysahibinden.common.navigation.FragmentNavigator
import com.muratcangzm.dummysahibinden.databinding.ExtendedFragmentLayoutBinding
import com.muratcangzm.dummysahibinden.ui.adapters.ExtendedAdapter
import com.muratcangzm.dummysahibinden.ui.fragments.core.BaseFragment
import com.muratcangzm.dummysahibinden.utils.NetworkConnection
import com.muratcangzm.dummysahibinden.viewmodels.ExtendedViewModel
import com.muratcangzm.dummysahibinden.viewmodels.core.ViewModelFactory
import com.muratcangzm.network.mapper.VehicleType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class ExtendedFragment : BaseFragment<ExtendedFragmentLayoutBinding>() {

    @get:LayoutRes
    override val layoutId: Int
        get() = R.layout.extended_fragment_layout


    private var type: VehicleType? = null
    private var id: Int? = null

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var extendedAdapter: ExtendedAdapter

    private lateinit var networkDialog: Dialog

    private val fragmentNavigator: FragmentNavigator by lazy { FragmentNavigator(this) }

    @VisibleForTesting
    private val viewModel: ExtendedViewModel by viewModels { viewModelFactory }


    private val args: ExtendedFragmentArgs by navArgs()

    private val exceptionHandler = CoroutineExceptionHandler{_, throwable ->
        Timber.tag("Extended Fragment Coroutine Error").d(throwable.message.toString())
    }

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): ExtendedFragmentLayoutBinding {
        return ExtendedFragmentLayoutBinding.inflate(inflater, container, false)
    }

    override fun networkDialog() {
        networkDialog = Dialog(requireContext())
        networkDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        networkDialog.setCancelable(false)
        networkDialog.setContentView(R.layout.internet_listener_dialog)
        networkDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val retryButton: MaterialButton = networkDialog.findViewById(R.id.reconnectButton)

        retryButton.setOnClickListener {
            if (NetworkConnection.isNetworkAvailable(requireContext())) {
                viewModel.fetchVehicleByBrandCode(type = type!!, codigo = id!!)

                networkDialog.dismiss()
            }
        }
        networkDialog.show()
    }

    override fun ExtendedFragmentLayoutBinding.initializeViews() {
        //not necessary in this class rn
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        extendedAdapter.setFragmentNavigation(fragmentNavigator)

        type = args.vehicleType
        id = args.id

        viewModel.fetchVehicleByBrandCode(type = type!!, codigo = id!!)


        Timber.d("receivedData: $type")
        Timber.d("receivedData: $id")

        setAdapter()

        lifecycleScope.launch(exceptionHandler) {

            viewModel.brandCodeData.collect {

                it?.let {

                    binding.extendedLoadingLayout.realLayout.visibility = View.GONE
                    binding.extendedRecyclerView.visibility = View.VISIBLE
                    binding.listTitle.visibility = View.VISIBLE
                    binding.spacer.visibility = View.VISIBLE

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

    override fun onStart() {
        super.onStart()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroyView() {
        super.onDestroyView()


    }


}