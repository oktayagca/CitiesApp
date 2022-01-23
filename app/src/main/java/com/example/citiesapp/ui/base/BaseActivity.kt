package com.example.citiesapp.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<viewBinding: ViewBinding>: AppCompatActivity() {

    private  var  _binding: viewBinding? = null
    val binding get() = _binding !!
    protected abstract fun getViewBinding():viewBinding
    private fun init() {
        _binding = getViewBinding()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
        setContentView(binding.root)
        initViews()
        observeData()
    }

    override fun onDestroy() {
        super.onDestroy()
        removeObject()
        _binding = null
    }
    abstract fun observeData()

    abstract fun initViews()

    open fun removeObject(){}
}