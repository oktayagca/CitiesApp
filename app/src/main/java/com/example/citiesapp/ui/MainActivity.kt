package com.example.citiesapp.ui

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.citiesapp.R
import com.example.citiesapp.databinding.ActivityMainBinding
import com.example.citiesapp.ui.base.BaseActivity
import com.example.citiesapp.ui.splash.SplashActivity
import com.example.citiesapp.utils.TOKEN
import com.example.citiesapp.utils.gone
import com.example.citiesapp.utils.show
import com.example.citiesapp.utils.showAlertDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {
    private lateinit var viewModel:MainActivitySharedViewModel

    override fun getViewBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun initViews() {
        viewModel =ViewModelProvider(this).get(MainActivitySharedViewModel::class.java)
        binding.apply {
            favToggleButton.gone()
            loginButton.show()
            signOutButton.gone()

            loginButton.setOnClickListener{
                Navigation.findNavController(this@MainActivity, R.id.navHostFragment).navigate(R.id.action_citiesFragment_to_loginRegisterFragment)
            }
            signOutButton.setOnClickListener{
                showAlertDialog("Oturum Sonlandır","Oturumu sonlandırmak istediğinize emin misiniz?","Kapat","Oturumu Sonlandır",{},{
                    viewModel.clearUserToken(TOKEN,"")
                    viewModel.setIsGuestUser(true)
                    val intent = Intent(this@MainActivity,SplashActivity::class.java)
                    startActivity(intent)
                    finish()
                })
            }
        }
    }

    override fun observeData() {
        viewModel.observeIsGuestUser().observe(this,{isGuestUser->
            binding.apply {
                if(isGuestUser){
                    textViewSpinner.text = isGuestUser.toString()
                    favToggleButton.gone()
                    loginButton.show()
                    signOutButton.gone()

                }else{
                    textViewSpinner.text = isGuestUser.toString()
                    favToggleButton.gone()
                    loginButton.gone()
                    signOutButton.show()
                }

            }
        })
    }
    fun hideAppBar(){
        binding.appBarLayout.gone()
    }
    fun showAppBar(){
        binding.appBarLayout.show()
        observeData()
    }

}