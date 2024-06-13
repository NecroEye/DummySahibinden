package com.muratcangzm.dummysahibinden.ui.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.muratcangzm.dummysahibinden.R
import com.muratcangzm.dummysahibinden.databinding.MainAdapterLayoutBinding
import com.muratcangzm.model.CarMarcasModel
import com.muratcangzm.network.mapper.VehicleType
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlin.jvm.Throws


class MainAdapter
@Inject
constructor(@ApplicationContext private val context: Context) :
    RecyclerView.Adapter<MainAdapter.MainHolder>() {

    private var mutableCarsModel = mutableListOf<CarMarcasModel>()
    private lateinit var binding: MainAdapterLayoutBinding
    private lateinit var vehicleType: VehicleType
    private var currentFragment: Fragment? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {

        binding = MainAdapterLayoutBinding.inflate(LayoutInflater.from(context), parent, false)

        return MainHolder()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    @Throws(ArrayIndexOutOfBoundsException::class)
    override fun getItemCount(): Int {
        return mutableCarsModel.size ?: 0
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        holder.setData(mutableCarsModel[position], vehicleType)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitCarMarcasModel(
        carMarcasModel: List<CarMarcasModel>?,
        type: VehicleType,
        fragment: Fragment
    ) {

        currentFragment = fragment
        Log.d("Alınan Fragment", "$currentFragment")

        carMarcasModel?.let {

            mutableCarsModel = carMarcasModel.toMutableList()
            vehicleType = type
            notifyDataSetChanged()


        } ?: emptyList<CarMarcasModel>()
    }

    inner class MainHolder : RecyclerView.ViewHolder(binding.root) {


        fun setData(data: CarMarcasModel, type: VehicleType) {

            val navController = currentFragment!!.findNavController()

            binding.apply {

                when (type) {
                    VehicleType.carros -> binding.vehicleType.text = "Car Brand: "
                    VehicleType.motos -> binding.vehicleType.text = "Motorcycle Brand: "
                    VehicleType.caminhoes -> binding.vehicleType.text = "Truck Brand: "
                }

                binding.vehicleNameHolderText.text = data.name.trim()

                binding.cardLayout.setOnClickListener {

                    val bundle = bundleOf("carTypeNumber" to data.id)

                    //i hope it works
                    navController
                        .navigate(
                            R.id.action_mainFragment_to_extendedFragment, bundle
                        )

                    Toast.makeText(context, "${data.name} clicked!", Toast.LENGTH_SHORT).show()

                }

            }

        }

    }

    @SuppressLint("NotifyDataSetChanged")
    private fun clearData() {
        mutableCarsModel.clear()
        mutableCarsModel = emptyList<CarMarcasModel>().toMutableList()
        notifyDataSetChanged()
    }


    override fun onViewDetachedFromWindow(holder: MainHolder) {
        super.onViewDetachedFromWindow(holder)
        clearData()
    }

}