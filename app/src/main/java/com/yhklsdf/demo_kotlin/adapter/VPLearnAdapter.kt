package com.yhklsdf.demo_kotlin.adapter

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.yhklsdf.demo_kotlin.fragment.ShowLearnFragment

class ViewPagerAdapter(val fm: FragmentManager): FragmentPagerAdapter (fm){

    val items = listOf("Android","iOS","PHP","JavaScript","Python")
    val urls = listOf("https://www.jikexueyuan.com/path/android",
            "https://www.jikexueyuan.com/path/ios",
            "https://www.jikexueyuan.com/path/php",
            "https://www.jikexueyuan.com/path/javascript",
            "https://www.jikexueyuan.com/path/python")

    override fun getItem(p0: Int): Fragment {
        val bundle = Bundle()
        bundle.putString("url",urls[p0])
        bundle.putString("type",items[p0])
        val fragment = ShowLearnFragment()
        fragment.arguments = bundle
        return fragment
    }

    override fun getCount(): Int {
        return items.size
    }

    /**
     * 为Tablayout而重写的一个方法
     */
    override fun getPageTitle(position: Int): CharSequence? {
        return items[position]
    }
}