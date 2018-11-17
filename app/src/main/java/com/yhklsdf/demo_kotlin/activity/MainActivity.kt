package com.yhklsdf.demo_kotlin.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import com.yhklsdf.demo_kotlin.R
import com.yhklsdf.demo_kotlin.fragment.EmptyFragment
import com.yhklsdf.demo_kotlin.fragment.LearnFragment
import com.yhklsdf.demo_kotlin.mediamvp.ShowPicturesFragment
import com.yhklsdf.demo_kotlin.utils.FragmentUtil
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    private fun initView() {
        var learnFragment = LearnFragment()
        val showPicturesFragment = ShowPicturesFragment()
        val emptyFragment = EmptyFragment()

        rg_main_bottom_navigation.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rbtn_main_bottom_learn -> FragmentUtil.replaceFragment(supportFragmentManager, learnFragment)
                R.id.rbtn_main_bottom_picture -> FragmentUtil.replaceFragment(supportFragmentManager, showPicturesFragment)
                R.id.rbtn_main_bottom_tools -> {
                    toast("工具")
                    FragmentUtil.replaceFragment(supportFragmentManager, emptyFragment)
                }
            }
        }
        rbtn_main_bottom_picture.isChecked = true
    }
}
