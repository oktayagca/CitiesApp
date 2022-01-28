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
    private var items: List<CitiesResponseItem>? = mutableListOf()
    private lateinit var onClickListener:ICitiesListener

    class CitiesViewHolder(private val binding: ItemCitiesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CitiesResponseItem,onClickListener: ICitiesListener) {
            binding.apply {
                textView.text = item.name
                rootViewItem.setOnClickListener{
                    onClickListener.onClick(item)
                }
            }

        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(items:List<CitiesResponseItem>,onClickListener: ICitiesListener){
        this.items = items
        this.onClickListener = onClickListener
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CitiesViewHolder {
        binding = ItemCitiesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CitiesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CitiesViewHolder, position: Int) {
        val item = items?.get(position)
        if (item != null) {
            holder.bind(item,onClickListener)
        }
    }

    override fun getItemCount(): Int = items!!.size
}