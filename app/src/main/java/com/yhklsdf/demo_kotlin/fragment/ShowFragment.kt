package com.yhklsdf.demo_kotlin.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yhklsdf.demo_kotlin.entity.ItemEntity
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg
import org.jsoup.Jsoup

class ShowFragment : Fragment() {

    private var mRecycleView : RecyclerView?=null
    /*表示可修改的*/
    private var mList = mutableListOf<ItemEntity>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (mRecycleView == null) {
            mRecycleView = RecyclerView(this!!.activity!!)
            mRecycleView!!.layoutManager = LinearLayoutManager(activity)

            val url = arguments?.getString("url")
            val type = arguments?.getString("type")
            
            initRecycleView(url)
        }
        return mRecycleView
    }

    private fun initRecycleView(url: String?) {
        async (UI){
            val result = bg {
                val jsoup = Jsoup.connect(url).get()
                /*class用. ,id用#*/
                val uls = jsoup.select("ul.cf")

                for (i in uls) {
                    val lis = i.select("li")
                    for (i in lis) {
                        val title = i.select("div.lesson-infor > h2 > a > font > font").text()
                        val url = i.select("div.lessonimg-box > a").text()
                        val describe = i.select("div.lesson-infor > p > font > font").text()
                        val image = i.select("div.lessonimg-box > a > img").attr("src")
                        val time_and_class = i.select("div.lesson-infor > div > div:nth-child(1) > dl > dd.mar-b8 > em > font > font").text()

                        val entity = ItemEntity(title = title, url = url, describe = describe, iamge = image, time_and_class = time_and_class, is_like = false)
                        mList.add(entity)
                    }
                }
            }


        }
    }
}