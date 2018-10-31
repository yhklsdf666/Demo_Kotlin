package com.yhklsdf.demo_kotlin.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yhklsdf.demo_kotlin.R
import com.yhklsdf.demo_kotlin.entity.ItemEntity

class MainRVAdapter(val mList: MutableList<ItemEntity>, val type: String) : RecyclerView.Adapter<MainRVAdapter.ViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(p0.context).inflate(R.layout.item_main_recycleview,p0,false))
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.bind(mList[p1])
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(itemEntity: ItemEntity) {

        }
    }
}
