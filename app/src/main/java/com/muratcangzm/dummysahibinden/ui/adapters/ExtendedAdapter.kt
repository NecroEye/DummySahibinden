package com.muratcangzm.dummysahibinden.ui.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.ColorSpace.Model
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
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


    private lateinit var binding: ExtendedAdapterLayoutBinding
    private var modelosModelAdapter: ModelosModel? = null
    private var fragmentNavigator: FragmentNavigator? = null
    private var type: VehicleType? = null
    private var id: Int? = null


    companion object {
        private const val VIEW_TYPE_ITEM = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExtendedHolder {

        binding = ExtendedAdapterLayoutBinding.inflate(LayoutInflater.from(context), parent, false)

        return ExtendedHolder()

    }

    @Throws(ArrayIndexOutOfBoundsException::class)
    override fun getItemCount(): Int {
        return modelosModelAdapter?.models?.size ?: 0
    }

    override fun getItemViewType(position: Int): Int {
        return VIEW_TYPE_ITEM
    }

    override fun onBindViewHolder(holder: ExtendedHolder, position: Int) {

        if (position < modelosModelAdapter?.models!!.size && position < modelosModelAdapter?.years!!.size)
            holder.setData(
                modelosModelAdapter!!.models[position],
                modelosModelAdapter!!.years[position]
            )
        else
            Timber.e("index $position which is out of bounds")

    }


    fun setList(modelosModel: ModelosModel, type: VehicleType, id: Int) {

        modelosModelAdapter = modelosModel
        val diffResult = DiffUtil.calculateDiff(DiffModelos(modelosModelAdapter!!, modelosModel))
        this.type = type
        this.id = id
        diffResult.dispatchUpdatesTo(this)
    }

    fun setFragmentNavigation(fragmentNav: FragmentNavigator) {

        this.fragmentNavigator = fragmentNav

    }

    inner class ExtendedHolder() : RecyclerView.ViewHolder(binding.root) {

        fun setData(dataOne: ModelosModel.Models, dataTwo: ModelosModel.Years) {

            binding.apply {

                vehicleModelName.text = dataOne.name
                yearText.text = dataTwo.name

                Timber.d("settled Data: $dataOne")

                extendedFragment.setOnClickListener {

                    type?.let { vehicleType ->
                        id?.let { vehicleId ->

                            fragmentNavigator?.navigateToDetails(
                                vehicleType,
                                vehicleId,
                                dataOne.code,
                                dataTwo.code
                            )

                            Toast.makeText(context, "clicked ${dataOne.name}", Toast.LENGTH_SHORT)
                                .show()


                        }
                    } ?: run {
                        Timber.e("navigation type or id is null")
                        Toast.makeText(
                            context,
                            "Error: Navigation data missing",
                            Toast.LENGTH_SHORT
                        ).show()
                    }


                }
            }
        }
    }

    inner class DiffModelos(
        private val oldModelosList: ModelosModel,
        private val newModelosList: ModelosModel
    ) : DiffUtil.Callback() {

        @Throws(ArrayIndexOutOfBoundsException::class)
        override fun getOldListSize(): Int {
            return oldModelosList.models.size ?: 0
        }

        @Throws(ArrayIndexOutOfBoundsException::class)
        override fun getNewListSize(): Int {
            return newModelosList.models.size ?: 0
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldModelosList.models[oldItemPosition].name == newModelosList.models[newItemPosition].name
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldModelosList.models == newModelosList.models
        }

    }
}