package com.example.citiesapp.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.citiesapp.databinding.ActivitySplashBinding
import com.example.citiesapp.ui.MainActivity
import com.example.citiesapp.ui.MainActivitySharedViewModel
import com.example.citiesapp.ui.base.BaseActivity
import com.example.citiesapp.utils.Resource
import com.example.citiesapp.utils.gone
import com.example.citiesapp.utils.show
import com.example.citiesapp.utils.showError
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity<ActivitySplashBinding>() {
    private val  viewModel:SplashViewModel by viewModels()

    override fun getViewBinding(): ActivitySplashBinding =
        ActivitySplashBinding.inflate(layoutInflater)

    override fun observeData() {
        if(viewModel.getUserToken().isNullOrEmpty()) {
            viewModel.observeGuestAuthData().observe(this, { response ->
                when (response.status) {
                    Resource.Status.LOADING -> {
                        binding.spinner.show()
                    }
                    Resource.Status.SUCCESS -> {
                        binding.spinner.gone()
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    Resource.Status.ERROR -> {
                        binding.spinner.gone()
                        showError(response.code!!, response.message!!)
                    }
                }
            })
        }else{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun initViews() {
    }

}