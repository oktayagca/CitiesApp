package com.example.citiesapp.ui.cities

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.citiesapp.CitiesApplication
import com.example.citiesapp.R
import com.example.citiesapp.data.entities.CitiesResponse
import com.example.citiesapp.data.entities.CitiesResponseItem
import com.example.citiesapp.databinding.FragmentCitiesListBinding
import com.example.citiesapp.ui.base.BaseFragment
import com.example.citiesapp.utils.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CitiesFragment: BaseFragment<FragmentCitiesListBinding>(),ICitiesListener,AppBarObserver{

    private val viewModel:CitiesViewModel by viewModels()
    private var adapter: CitiesRecyclerViewAdapter = CitiesRecyclerViewAdapter()

    override fun getViewBinding(): FragmentCitiesListBinding {
       return FragmentCitiesListBinding.inflate(layoutInflater)
    }

    override fun observeData() {
       viewModel.observeCitiesList().observe(viewLifecycleOwner,{response ->
           when(response.status){
               Resource.Status.LOADING->{
                   binding.spinner.show()
               }
               Resource.Status.SUCCESS-> {
                   binding.spinner.gone()
                   adapter.setData(response.data!!,this)
               }
               Resource.Status.ERROR ->{
                   binding.spinner.gone()
                   requireContext().showError(response.code!!,response.message!!)
               }
           }
       })
    }

    override fun initViews() {
        (requireActivity().application as CitiesApplication).registerObserver(this)
        binding.apply {
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(context)
        }
    }

    private fun observeFavourites() {
        viewModel.observeFavouritesList().observe(viewLifecycleOwner,{response->
            when(response.status){
                Resource.Status.LOADING->{
                    binding.spinner.show()
                }
                Resource.Status.SUCCESS->{
                    binding.spinner.gone()
                    setData(response.data!!)
                }
                Resource.Status.ERROR ->{
                    binding.spinner.gone()
                    requireActivity().showError(response.code!!,response.message!!)
                }
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        (requireActivity().application as CitiesApplication).unRegisterObserver(this)
    }
    override fun onClick(city:CitiesResponseItem) {
        if(viewModel.isGuestUser()){
            findNavController().navigate(R.id.action_citiesFragment_to_loginRegisterFragment)
        }else{
            val action = CitiesFragmentDirections.actionCitiesFragmentToDistrictsListFragment(city)
            findNavController().navigate(action)
        }
    }

    override fun onChange(change: Boolean, changedData: AppBarObserverEnum) {
        if (change) {
            when (changedData) {
                AppBarObserverEnum.FETCH_CITIES->{
                    observeData()
                }
                AppBarObserverEnum.FETCH_FAVOURITES ->{
                    viewModel.getFavourites()
                    observeFavourites()
                }
                AppBarObserverEnum.FETCH_CITIES_NOT_OBSERVE->{
                    viewModel.getCitiesList()
                }
            }
        }
    }

    private fun setData(list: CitiesResponse) {
        adapter.setData(list,this)
    }

    override fun removeObject() {
        super.removeObject()
        binding.recyclerView.adapter = null
    }
}