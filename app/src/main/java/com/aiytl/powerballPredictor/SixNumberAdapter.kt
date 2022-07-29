package com.aiytl.powerballPredictor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aiytl.powerballPredictor.model.SixNumber

class SixNumberAdapter (private val sixNumbersList : List<SixNumber>) : RecyclerView.Adapter<SixNumberAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.layout_rows_rv, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val numbers : SixNumber? = sixNumbersList[position]
        holder.tvSNo.text = (position.plus(1)).toString()
        holder.tvNum1.text = numbers!!.num1.toString()
        holder.tvNum2.text = numbers!!.num2.toString()
        holder.tvNum3.text = numbers!!.num3.toString()
        holder.tvNum4.text = numbers!!.num4.toString()
        holder.tvNum5.text = numbers!!.num5.toString()
        holder.tvNum6.text = numbers!!.num6.toString()
    }

    override fun getItemCount(): Int {
        return sixNumbersList.size
    }

    public class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val tvNum1 : TextView = itemView.findViewById(R.id.tv11)
        val tvNum2 : TextView = itemView.findViewById(R.id.tv22)
        val tvNum3 : TextView = itemView.findViewById(R.id.tv33)
        val tvNum4 : TextView = itemView.findViewById(R.id.tv44)
        val tvNum5 : TextView = itemView.findViewById(R.id.tv55)
        val tvNum6 : TextView = itemView.findViewById(R.id.tv66)
        val tvSNo : TextView = itemView.findViewById(R.id.tv_sno)
    }
}