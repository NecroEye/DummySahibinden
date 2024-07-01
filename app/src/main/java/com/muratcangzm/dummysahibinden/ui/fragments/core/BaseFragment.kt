package com.muratcangzm.dummysahibinden.ui.fragments.core

import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.muratcangzm.dummysahibinden.common.listener.NetworkListener

abstract class BaseFragment<VB : ViewBinding> : Fragment() {

    private var _binding: VB? = null
    protected val binding: VB
        get() = _binding ?: error("fragment view not initialized")

    @get:LayoutRes
    protected abstract val layoutId: Int

    private lateinit var networkChangeReceiver: NetworkListener

    protected abstract fun inflateBinding(inflater: LayoutInflater, container: ViewGroup?): VB


    protected abstract fun networkDialog()

    protected abstract fun VB.initializeViews()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = inflateBinding(inflater, container)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.initializeViews()
    }

    override fun onStart() {
        super.onStart()

        networkChangeReceiver = NetworkListener { isConnected ->

            if (isConnected)
                println("is connected")
            else
                networkDialog()
        }

        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        requireContext().registerReceiver(networkChangeReceiver, filter)

    }

    override fun onStop() {
        super.onStop()
        context?.unregisterReceiver(networkChangeReceiver)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}