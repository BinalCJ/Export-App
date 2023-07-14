package com.example.xportmaster.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.xportmaster.R
import com.example.xportmaster.models.ExporterModel

class ExpAdapter(private val expList: ArrayList<ExporterModel>) : RecyclerView.Adapter<ExpAdapter.ViewHolder>() {

    private lateinit var mListner: onItemClickListner

    interface onItemClickListner {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListner(clickListner: onItemClickListner) {
        mListner = clickListner
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.exp_list_item, parent, false)
        return ViewHolder(itemView, mListner)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentExp = expList[position]
        holder.tvExpName.text = currentExp.User_Name
    }

    override fun getItemCount(): Int {
        return expList.size
    }

    class ViewHolder(itemView: View, clickListner: onItemClickListner) : RecyclerView.ViewHolder(itemView) {
        val tvExpName : TextView = itemView.findViewById(R.id.tvExpName)

        init {
            itemView.setOnClickListener {
                clickListner.onItemClick(adapterPosition)
            }
        }

    }
}












