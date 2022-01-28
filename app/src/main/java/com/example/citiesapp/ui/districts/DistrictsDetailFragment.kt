package com.example.citiesapp.ui.districts

import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.citiesapp.data.entities.DistrictsResponseItem
import com.example.citiesapp.databinding.FragmentDistrictDetailBinding
import com.example.citiesapp.ui.MainActivity
import com.example.citiesapp.ui.base.BaseFragment

class DistrictsDetailFragment:BaseFragment<FragmentDistrictDetailBinding>() {
    private val args: DistrictsDetailFragmentArgs by navArgs()
    private lateinit var district:DistrictsResponseItem
    override fun getViewBinding(): FragmentDistrictDetailBinding {
        return FragmentDistrictDetailBinding.inflate(layoutInflater)
    }

    override fun initViews() {
        super.initViews()
        district = args.district
        binding.apply {
            Glide.with(imageView.context)
                .load(district.image).into(imageView)
            description.text = district.description
        }
    }

    override fun onResume() {
        super.onResume()
        (requireActivity() as MainActivity).appBarChangeTextAndShowBackButton(null,district,false)
    }

}