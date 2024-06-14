package com.muratcangzm.dummysahibinden.common.navigation

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.muratcangzm.dummysahibinden.R
import com.muratcangzm.network.mapper.VehicleType

class FragmentNavigator(private val fragment: Fragment) {


    fun navigateToExtendedFragment(
        type: VehicleType,
        id: Int
    ) {

        val bundle = bundleOf("vehicleType" to type, "id" to id)
        fragment.findNavController().navigate(R.id.action_mainFragment_to_extendedFragment, bundle)

    }

    fun navigateToDetails(
        type: VehicleType,
        id: Int,
        brandCode: Int,
        date: String
    ) {

        val bundle = bundleOf(
            "vehicleArgs" to type,
            "marcaArgs" to id,
            "codigoArgs" to brandCode,
            "dateArgs" to date
        )

        fragment.findNavController()
            .navigate(R.id.action_extendedFragment_to_detailsFragment, bundle)

    }

}