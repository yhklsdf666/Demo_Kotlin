package com.yhklsdf.demo_kotlin.fragment

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.yhklsdf.demo_kotlin.LottieActivity
import com.yhklsdf.demo_kotlin.R
import com.yhklsdf.demo_kotlin.adapter.ViewPagerAdapter

class LearnFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_kotlin, null)
        val viewPager = view.findViewById<ViewPager>(R.id.main_viewpager)
        val tabLayout = view.findViewById<TabLayout>(R.id.main_tab_layout)
        val imageView = view.findViewById<ImageView>(R.id.imageView)
        //getFragmentManager到的是activity对所包含fragment的Manager，而如果是fragment嵌套fragment，那么就需要利用getChildFragmentManager()了,此处用childFragmentManager，不然切换数据会消失
        viewPager.adapter = ViewPagerAdapter(childFragmentManager)
        tabLayout.setupWithViewPager(viewPager)
        imageView.setOnClickListener {
            val intent = Intent(activity, LottieActivity::class.java)
            startActivity(intent)
//            val view = LayoutInflater.from(this).inflate(R.layout.item_lottie, null)
//            val dialog = AlertDialog.Builder(this@LearnFragment)
////            设置点击对话框内容之外对话框消失
////            dialog.setCanceledOnTouchOutside(true)
//            dialog.setView(view)
//            dialog.show()
        }
        return view
    }
}

