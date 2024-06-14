package com.muratcangzm.dummysahibinden.ui.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.ColorSpace.Model
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.muratcangzm.dummysahibinden.common.navigation.FragmentNavigator
import com.muratcangzm.dummysahibinden.databinding.ExtendedAdapterLayoutBinding
import com.muratcangzm.model.ModelosModel
import com.muratcangzm.network.mapper.VehicleType
import dagger.hilt.android.qualifiers.ApplicationContext
import timber.log.Timber
import javax.inject.Inject
import kotlin.jvm.Throws

class ExtendedAdapter
@Inject
constructor(@ApplicationContext private val context: Context) :
    RecyclerView.Adapter<ExtendedAdapter.ExtendedHolder>() {

    //TODO use databinding later

    private lateinit var binding: ExtendedAdapterLayoutBinding
    private var modelosModelAdapter: ModelosModel? = null
    private var fragmentNavigator: FragmentNavigator? = null
    private var type: VehicleType? = null
    private var id: Int? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExtendedHolder {

        binding = ExtendedAdapterLayoutBinding.inflate(LayoutInflater.from(context), parent, false)

        return ExtendedHolder()

    }

    @Throws(ArrayIndexOutOfBoundsException::class)
    override fun getItemCount(): Int {
        return modelosModelAdapter?.models?.size ?: 0
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onBindViewHolder(holder: ExtendedHolder, position: Int) {

        holder.setData(
            modelosModelAdapter!!.models[position],
            modelosModelAdapter!!.years[position]
        )

    }


    @SuppressLint("NotifyDataSetChanged")
    fun setList(modelosModel: ModelosModel, type: VehicleType, id: Int) {

        this.type = type
        this.id = id
        modelosModelAdapter = modelosModel
        notifyDataSetChanged()

    }

    fun setFragmentNavigation(fragmentNav: FragmentNavigator) {

        this.fragmentNavigator = fragmentNav

    }


    inner class ExtendedHolder() : RecyclerView.ViewHolder(binding.root) {

        fun setData(dataOne: ModelosModel.Models, dataTwo: ModelosModel.Years) {

            binding.apply {

                vehicleModelName.text = dataOne.name
                yearText.text = dataTwo.name

                Timber.d("settled Data: ${dataOne}")

                extendedFragment.setOnClickListener {

                     //TODO fix it later prepare smooth navigation data class for it
                     //fragmentNavigator!!.navigateToDetails(type!!, id!!, dataOne.code, dataTwo.code)
                    Toast.makeText(context, "clicked ${dataOne.name}", Toast.LENGTH_SHORT).show()

                }

            }
        }
    }
}