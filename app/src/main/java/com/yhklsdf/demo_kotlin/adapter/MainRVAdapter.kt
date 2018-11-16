package com.yhklsdf.demo_kotlin.adapter

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.yhklsdf.demo_kotlin.DetailActivity
import com.yhklsdf.demo_kotlin.R
import com.yhklsdf.demo_kotlin.db.database
import com.yhklsdf.demo_kotlin.entity.ItemEntity
import kotlinx.android.synthetic.main.item_learn_recycleview.view.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.db.update

class MainRVAdapter(val mList: MutableList<ItemEntity>, val type: String) : RecyclerView.Adapter<MainRVAdapter.ViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(p0.context).inflate(R.layout.item_learn_recycleview, p0, false))
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.bind(mList[p1])
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(itemEntity: ItemEntity) {
            itemView.adapter_title.text = itemEntity.title
            itemView.adapter_des.text = itemEntity.describe
            itemView.adapter_time_and_class.text = itemEntity.time_and_class

            if (itemEntity.is_like == true) {
                itemView.adapter_like.setImageResource(R.mipmap.ic_love_select)
            } else {
                itemView.adapter_like.setImageResource(R.mipmap.ic_love_normal)
            }

            Glide.with(itemView.context).load(itemEntity.iamge).into(itemView.adapter_img)

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailActivity::class.java)
                intent.putExtra("url", itemEntity.url)
                intent.putExtra("title", itemEntity.title)
                intent.putExtra("is_like", itemEntity.is_like)
                intent.putExtra("type", type)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                itemView.context.startActivity(intent)
            }

            itemView.adapter_like.setOnClickListener {
                itemEntity.is_like = !itemEntity.is_like
                if (itemEntity.is_like == true) {
                    itemView.adapter_like.setImageResource(R.mipmap.ic_love_select)
                } else {
                    itemView.adapter_like.setImageResource(R.mipmap.ic_love_normal)
                }
                async(UI) {
                    val task = bg {
                        itemView.context.database.use {
                            select("Like", "id").whereArgs("(type == {type})  and (url = {url})",
                                    "type" to type, "url" to itemEntity.url).exec {
                                if (count > 0) {
                                    update("Like", "is_like" to itemEntity.is_like)
                                            .whereArgs("(type == {type})  and (url = {url})",
                                                    "type" to type, "url" to itemEntity.url).exec().toLong()
                                } else {
                                    insert("Like", "type" to type, "url" to itemEntity.url, "is_like" to itemEntity.is_like)
                                }
                            }
                        }
                    }
                    task.await()
                }
            }
        }
    }
}
