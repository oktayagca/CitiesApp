package com.example.citiesapp.ui.cities

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.citiesapp.R
import com.example.citiesapp.data.entities.CitiesResponseItem
import com.example.citiesapp.databinding.FragmentCitiesListBinding
import com.example.citiesapp.ui.base.BaseFragment
import com.example.citiesapp.utils.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CitiesFragment: BaseFragment<FragmentCitiesListBinding>(),ICitiesListener{

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
        binding.apply {
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(context)
        }
    }

    override fun onClick(city:CitiesResponseItem) {
        if(viewModel.isGuestUser()){
            findNavController().navigate(R.id.action_citiesFragment_to_loginRegisterFragment)
        }else{
            viewModel.getDistricts(cityId = city.id.toString()).observe(viewLifecycleOwner,{response->
                when(response.status){
                    Resource.Status.LOADING->{

                    }
                    Resource.Status.SUCCESS->{
                    }
                    Resource.Status.ERROR->{
                        requireActivity().showError(response.code!!,response.message!!)
                    }
                }
            })
        }
    }

}