package com.aiytl.powerballPredictor.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aiytl.powerballPredictor.databinding.LayoutRowBinding

class MyNumbersAdapter(private val numberList : List<String>, private val selectedNumber : ArrayList<String>) : RecyclerView.Adapter<MyNumbersAdapter.MyViewHolder>() {

    inner class MyViewHolder(val binding : LayoutRowBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = LayoutRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.tvData.text = numberList[position]


    }

    override fun getItemCount(): Int {
        return numberList.size
    }
}