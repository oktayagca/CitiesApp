package com.example.citiesapp.ui.cities

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.citiesapp.data.entities.CitiesResponseItem
import com.example.citiesapp.databinding.ItemCitiesBinding

class CitiesRecyclerViewAdapter :
    RecyclerView.Adapter<CitiesRecyclerViewAdapter.CitiesViewHolder>() {

    private lateinit var binding: ItemCitiesBinding
    private var items: List<CitiesResponseItem> = mutableListOf()

    class CitiesViewHolder(private val binding: ItemCitiesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CitiesResponseItem) {
            binding.apply {
                textView.text = item.name
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(items:List<CitiesResponseItem>){
        this.items = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CitiesViewHolder {
        binding = ItemCitiesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CitiesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CitiesViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount() = items.size
}