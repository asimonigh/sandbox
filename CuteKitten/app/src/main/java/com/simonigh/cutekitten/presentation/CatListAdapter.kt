package com.simonigh.cutekitten.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.simonigh.cutekitten.data.model.Cat
import com.simonigh.cutekitten.databinding.ItemCatBinding
import com.simonigh.cutekitten.presentation.CatListAdapter.ViewHolder

class CatListAdapter() : RecyclerView.Adapter<ViewHolder>() {
    
    private val itemsList = mutableListOf<Cat>()
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCatBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }
    
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(itemsList[position])
    }
    
    override fun getItemCount() = itemsList.size
    
    fun addItems(cats: List<Cat>){
        itemsList.addAll(cats)
        notifyDataSetChanged()
    }
    
    fun getIdAtPosition(position: Int): String {
        return itemsList[position - 1].id
    }
    
    fun clear() = itemsList.clear()
    
    class ViewHolder(private val binding: ItemCatBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(cat: Cat){
            Glide.with(itemView)
                .load(cat.url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.itemCardImage)
        }
    }
}