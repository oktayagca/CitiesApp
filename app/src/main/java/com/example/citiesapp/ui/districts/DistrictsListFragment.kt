package com.example.citiesapp.ui.districts

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.citiesapp.data.entities.CitiesResponseItem
import com.example.citiesapp.data.entities.DistrictsResponseItem
import com.example.citiesapp.databinding.FragmentDistrictsListBinding
import com.example.citiesapp.ui.MainActivity
import com.example.citiesapp.ui.base.BaseFragment
import com.example.citiesapp.utils.Resource
import com.example.citiesapp.utils.gone
import com.example.citiesapp.utils.show
import com.example.citiesapp.utils.showError
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DistrictsListFragment: BaseFragment<FragmentDistrictsListBinding>(),IDistrictsListener {

    private val viewModel: DistrictsViewModel by viewModels()
    private var adapter: DistrictsRecyclerViewAdapter = DistrictsRecyclerViewAdapter()
    private lateinit var city:CitiesResponseItem
    private val args: DistrictsListFragmentArgs by navArgs()

    override fun getViewBinding(): FragmentDistrictsListBinding {
       return FragmentDistrictsListBinding.inflate(layoutInflater)
    }

    override fun initViews() {
        super.initViews()
        city = args.city
        binding.apply {
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(context)
            addLayoutRoot.setOnClickListener{
                onClickAddButton()
            }
        }
        (requireActivity() as MainActivity).appBarChangeTextAndShowBackButton(city,null,true)
    }

    override fun observeData() {
        super.observeData()
        viewModel.getDistricts(cityId = city.id.toString()).observe(viewLifecycleOwner,{response->
            when(response.status){
                Resource.Status.LOADING->{
                    binding.spinner.show()
                }
                Resource.Status.SUCCESS->{
                    binding.spinner.gone()
                    setData(response.data!!)
                }
                Resource.Status.ERROR->{
                    binding.spinner.gone()
                    requireActivity().showError(response.code!!,response.message!!)
                }
            }
        })
    }

    private fun setData(items:List<DistrictsResponseItem>){
        if(items.isNullOrEmpty()){
            binding.recyclerView.gone()
            binding.addLayoutRoot.show()
        }else{
            binding.recyclerView.show()
            binding.addLayoutRoot.gone()
            adapter.setData(items,this)
        }

    }

    override fun onClick(district: DistrictsResponseItem) {
        val action = DistrictsListFragmentDirections.actionDistrictsListFragmentToDistrictsDetailFragment(district)
        findNavController().navigate(action)
    }

    override fun onClickAddButton() {
        val action = DistrictsListFragmentDirections.actionDistrictsListFragmentToAddDistrictFragment(cityId = city.id)
        findNavController().navigate(action)
    }

    override fun removeObject() {
        super.removeObject()
        (requireActivity() as MainActivity).appBarChangeTextAndHideBackButton()
        binding.recyclerView.adapter = null
    }

}