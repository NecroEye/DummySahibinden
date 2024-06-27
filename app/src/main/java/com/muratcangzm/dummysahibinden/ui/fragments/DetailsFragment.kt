package com.muratcangzm.dummysahibinden.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.VisibleForTesting
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.muratcangzm.dummysahibinden.R
import com.muratcangzm.dummysahibinden.databinding.DetailsFragmentLayoutBinding
import com.muratcangzm.dummysahibinden.ui.fragments.core.BaseFragmentDataBinding
import com.muratcangzm.dummysahibinden.viewmodels.DetailsViewModel
import com.muratcangzm.dummysahibinden.viewmodels.core.ViewModelFactory
import com.muratcangzm.network.mapper.VehicleType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class DetailsFragment : BaseFragmentDataBinding<DetailsFragmentLayoutBinding>() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @VisibleForTesting
    private val viewModel: DetailsViewModel by viewModels { viewModelFactory }

    private var type: VehicleType? = null
    private var marcas: Int? = null
    private var codigo: Int? = null
    private var date: String? = null

    private val args: DetailsFragmentArgs by navArgs()

    override val layoutId: Int
        get() = R.layout.details_fragment_layout

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Timber.tag("Detail Fragment Coroutine Error").d(throwable.message.toString())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        type = args.vehicleArgs
        marcas = args.marcaArgs
        codigo = args.codigoArgs
        date = args.dateArgs

        Timber.tag("DetailsFragment").w("Bunlar $date $codigo $marcas $type")

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.fetchVehicleDetails(type!!, marcas!!, codigo!!, date!!)


        lifecycleScope.launch(exceptionHandler) {

            viewModel.vehicleDetails.collectLatest { result ->
                result?.let {
                    Timber.tag("Details Data").w("Detail Gelen: $it")

                }
            }

        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

}