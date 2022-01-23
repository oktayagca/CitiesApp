package com.example.citiesapp.ui.cities

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.citiesapp.databinding.FragmentCitiesListBinding
import com.example.citiesapp.ui.base.BaseFragment
import com.example.citiesapp.utils.Resource
import com.example.citiesapp.utils.gone
import com.example.citiesapp.utils.show
import com.example.citiesapp.utils.showError
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CitiesFragment: BaseFragment<FragmentCitiesListBinding>() {

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
                   adapter.setData(response.data!!)
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

}