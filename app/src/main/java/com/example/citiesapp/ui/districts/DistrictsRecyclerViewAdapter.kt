package com.example.citiesapp.ui.districts

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.citiesapp.data.entities.DistrictsResponseItem
import com.example.citiesapp.databinding.ItemCitiesBinding
import com.example.citiesapp.utils.show

class DistrictsRecyclerViewAdapter:RecyclerView.Adapter<DistrictsRecyclerViewAdapter.DistrictsViewHolder>() {

    private lateinit var binding: ItemCitiesBinding
    private var items: List<DistrictsResponseItem>? = mutableListOf()
    private lateinit var onClickListener:IDistrictsListener

    class DistrictsViewHolder(private val binding: ItemCitiesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: DistrictsResponseItem?, onClickListener: IDistrictsListener,itemCount:Int) {
            binding.apply {
                textView.text = item?.name
                rootViewItem.setOnClickListener{
                    item?.let {
                        onClickListener.onClick(item)
                    }
                }
                if(adapterPosition == itemCount-1){
                    addLayoutRoot.show()
                    addLayoutRoot.setOnClickListener{
                        onClickListener.onClickAddButton()
                    }
                }
            }

        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(items:List<DistrictsResponseItem>,onClickListener: IDistrictsListener){
        this.items = items
        this.onClickListener = onClickListener
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DistrictsViewHolder {
        binding = ItemCitiesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DistrictsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DistrictsViewHolder, position: Int) {
        val item = items?.get(position)
        if (item != null) {
            holder.bind(item,onClickListener,itemCount)
        }else{
            holder.bind(null,onClickListener,itemCount)
        }
    }

    override fun getItemCount(): Int = items!!.size
}