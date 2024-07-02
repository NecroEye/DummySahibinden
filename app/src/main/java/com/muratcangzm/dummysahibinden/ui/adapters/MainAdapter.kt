package com.muratcangzm.dummysahibinden.ui.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.muratcangzm.dummysahibinden.R
import com.muratcangzm.dummysahibinden.common.navigation.FragmentNavigator
import com.muratcangzm.dummysahibinden.databinding.MainAdapterLayoutBinding
import com.muratcangzm.model.CarMarcasModel
import com.muratcangzm.network.mapper.VehicleType
import dagger.hilt.android.qualifiers.ApplicationContext
import timber.log.Timber
import javax.inject.Inject
import kotlin.jvm.Throws


class MainAdapter
@Inject
constructor(@ApplicationContext private val context: Context) :
    RecyclerView.Adapter<MainAdapter.MainHolder>() {

    var mutableCarsModel = mutableListOf<CarMarcasModel>()
    private lateinit var binding: MainAdapterLayoutBinding
    private lateinit var vehicleType: VehicleType
    private var currentFragment: Fragment? = null
    private var navigationDestination: FragmentNavigator? = null


    companion object {
        private const val VIEW_TYPE_ITEM = 1
        private const val TAG = "MainAdapter"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {

        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.main_adapter_layout, parent, false)

        return MainHolder()
    }

    override fun getItemViewType(position: Int): Int {
        return VIEW_TYPE_ITEM
    }

    @Throws(ArrayIndexOutOfBoundsException::class)
    override fun getItemCount(): Int {
        return mutableCarsModel.size ?: 0
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        holder.setData(mutableCarsModel[position], vehicleType)

        binding.carMarcas = mutableCarsModel[position]

    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitCarMarcasModel(
        carMarcasModel: List<CarMarcasModel>?,
        type: VehicleType,
        fragment: Fragment
    ) {

        currentFragment = fragment
        Timber.tag("Alınan Fragment").d("$currentFragment")

        carMarcasModel?.let {

            val diffResult = DiffUtil.calculateDiff(CarDiffUtils(mutableCarsModel, carMarcasModel))
            mutableCarsModel.clear()
            mutableCarsModel.addAll(carMarcasModel)
            vehicleType = type
            diffResult.dispatchUpdatesTo(this)

        } ?: run {
            mutableCarsModel.clear()
            notifyDataSetChanged()
        }
    }

    fun setFragmentNavigation(fragmentNav: FragmentNavigator) {

        this.navigationDestination = fragmentNav

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


                binding.cardLayout.setOnClickListener {

                    navigationDestination?.navigateToExtendedFragment(type, data.id)


                    Toast.makeText(context, "${data.id} clicked!", Toast.LENGTH_SHORT).show()

                }

            }

        }

    }

    inner class CarDiffUtils(
        private val mutableOldList: List<CarMarcasModel>,
        private val mutableNewList: List<CarMarcasModel>
    ) : DiffUtil.Callback() {

        @Throws(ArrayIndexOutOfBoundsException::class)
        override fun getOldListSize(): Int {
            return mutableOldList.size ?: 0
        }

        @Throws(ArrayIndexOutOfBoundsException::class)
        override fun getNewListSize(): Int {
            return mutableNewList.size ?: 0
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return mutableOldList[oldItemPosition].id == mutableNewList[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return mutableOldList[oldItemPosition] == mutableNewList[newItemPosition]
        }


    }

}