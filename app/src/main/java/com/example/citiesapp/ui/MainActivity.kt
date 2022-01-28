package com.example.citiesapp.ui

import android.content.Intent
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.citiesapp.R
import com.example.citiesapp.databinding.ActivityMainBinding
import com.example.citiesapp.ui.base.BaseActivity
import com.example.citiesapp.ui.splash.SplashActivity
import dagger.hilt.android.AndroidEntryPoint
import com.example.citiesapp.CitiesApplication
import com.example.citiesapp.data.entities.AddAndRemoveFavouritesRequest
import com.example.citiesapp.data.entities.CitiesResponseItem
import com.example.citiesapp.data.entities.DistrictsResponseItem
import com.example.citiesapp.utils.*


@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(), AppBarObservable {
    private lateinit var viewModel: MainActivitySharedViewModel
    private var isGuestUser: Boolean = false
    private lateinit var selectedMenu: String
    private var city: CitiesResponseItem? = null

    override fun getViewBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun initViews() {
        selectedMenu = getString(R.string.cities)
        viewModel = ViewModelProvider(this).get(MainActivitySharedViewModel::class.java)
        binding.apply {
            favToggleButton.gone()
            loginButton.show()
            signOutButton.gone()

            loginButton.setOnClickListener {
                Navigation.findNavController(this@MainActivity, R.id.navHostFragment)
                    .navigate(R.id.action_citiesFragment_to_loginRegisterFragment)
            }
            signOutButton.setOnClickListener {
                showAlertDialog(
                    "Oturum Sonlandır",
                    "Oturumu sonlandırmak istediğinize emin misiniz?",
                    "Kapat",
                    "Oturumu Sonlandır",
                    {},
                    {
                        viewModel.clearUserToken(TOKEN, "")
                        viewModel.setIsGuestUser(true)
                        val intent = Intent(this@MainActivity, SplashActivity::class.java)
                        startActivity(intent)
                        finish()
                    })
            }

            textViewSpinner.setOnClickListener {
                showMenuPopup()
            }
            favToggleButton.setOnClickListener {
                if (favToggleButton.isChecked) {
                    viewModel.addCityFavourites(AddAndRemoveFavouritesRequest(city!!.id))
                        .observe(this@MainActivity, { response ->
                            when (response.status) {
                                Resource.Status.LOADING -> {
                                }
                                Resource.Status.SUCCESS -> {
                                    showAlertDialog(
                                        "İşlem başarılı",
                                        "Kaydedildi",
                                        "Ok",
                                        null,
                                        {},
                                        {})
                                }
                                Resource.Status.ERROR -> {
                                    showError(response.code!!, response.message!!)
                                }
                            }
                        })
                } else {
                    viewModel.removeCityFavourites(AddAndRemoveFavouritesRequest(city!!.id))
                        .observe(this@MainActivity, { response ->
                            when (response.status) {
                                Resource.Status.LOADING -> {
                                }
                                Resource.Status.SUCCESS -> {
                                    showAlertDialog(
                                        "İşlem başarılı",
                                        "Kaydedildi",
                                        "Ok",
                                        null,
                                        {},
                                        {})
                                }
                                Resource.Status.ERROR -> {
                                    showError(response.code!!, response.message!!)
                                }
                            }
                        })
                }

                notifyObservers(true, AppBarObserverEnum.FETCH_CITIES_NOT_OBSERVE)
            }

            backPressLayout.setOnClickListener {
                goBack()
            }
        }
    }

    override fun observeData() {
        viewModel.observeIsGuestUser().observe(this, { isGuestUser ->
            this.isGuestUser = isGuestUser
            binding.apply {
                if (isGuestUser) {
                    favToggleButton.gone()
                    loginButton.show()
                    signOutButton.gone()

                } else {
                    favToggleButton.gone()
                    loginButton.gone()
                    signOutButton.show()
                }

            }
        })
    }

    fun hideAppBar() {
        binding.appBarLayout.gone()
    }

    fun showAppBar() {
        binding.appBarLayout.show()
        observeData()
    }

    fun appBarChangeTextAndShowBackButton(city: CitiesResponseItem?,districts:DistrictsResponseItem?,isFavouriteShow:Boolean) {
        binding.appBarLayout.show()
        this.city = city
        binding.apply {
            selectedMenu = getString(R.string.cities)
            textViewSpinner.gone()
            imageView.gone()
            backPressLayout.show()
            if(city !=null){
                textViewBackButton.text = city.name
                favToggleButton.isChecked = city.faved
            }else if(districts!=null){
                textViewBackButton.text = districts.name
            }
            if(isFavouriteShow){
                favToggleButton.show()
            }
            loginButton.gone()
            signOutButton.gone()
        }
    }

    fun appBarChangeTextAndHideBackButton() {
        binding.apply {
            textViewSpinner.show()
            imageView.show()
            backPressLayout.gone()
            textViewSpinner.text = selectedMenu
            favToggleButton.gone()
            loginButton.gone()
            signOutButton.show()
        }
    }

    private fun showMenuPopup() {
        val inflater = this.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater

        // OUTSIDE BACKGROUND COLOR
        val layout: View = inflater.inflate(
            R.layout.popup_bg,
            findViewById(R.id.fadePopup)
        )
        val fadePopup = PopupWindow(
            layout,
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT,
            false
        )
        fadePopup.showAtLocation(findViewById(android.R.id.content), Gravity.NO_GRAVITY, 0, 0)
        // OUTSIDE BACKGROUND COLOR
        val customView: View = inflater.inflate(R.layout.menu_popup, null)
        val mPopupWindow = PopupWindow(
            customView,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        mPopupWindow.elevation = 5.0f

        val citiesButton = customView.findViewById<TextView>(R.id.citiesButton)
        citiesButton.setOnClickListener {
            binding.textViewSpinner.text = getString(R.string.cities)
            fadePopup.dismiss()
            mPopupWindow.dismiss()
            selectedMenu = getString(R.string.cities)
            notifyObservers(true, AppBarObserverEnum.FETCH_CITIES)
        }
        val favouritesButton = customView.findViewById<TextView>(R.id.favouritesButton)
        favouritesButton.setOnClickListener {
            if (isGuestUser) {
                Navigation.findNavController(this@MainActivity, R.id.navHostFragment)
                    .navigate(R.id.action_citiesFragment_to_loginRegisterFragment)
            } else {
                binding.textViewSpinner.text = getString(R.string.favourites)
                selectedMenu = getString(R.string.favourites)
                notifyObservers(true, AppBarObserverEnum.FETCH_FAVOURITES)
            }

            fadePopup.dismiss()
            mPopupWindow.dismiss()
        }
        mPopupWindow.update()
        mPopupWindow.showAtLocation(findViewById(android.R.id.content), Gravity.CENTER, 0, 0)
    }

    private fun goBack() {
        if (!Navigation.findNavController(this@MainActivity, R.id.navHostFragment).popBackStack()) {
            finish()
        }
    }

    override fun notifyObservers(change: Boolean, changedData: AppBarObserverEnum) {
        val list: List<AppBarObserver> = (application as CitiesApplication).getObserverList()
        for (i in list) {
            i.onChange(change, changedData)
        }
    }

}