package com.yhklsdf.demo_kotlin.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.yhklsdf.demo_kotlin.R
import com.yhklsdf.demo_kotlin.fragment.EmptyFragment
import com.yhklsdf.demo_kotlin.fragment.LearnFragment
import com.yhklsdf.demo_kotlin.fragment.MediaFragment
import com.yhklsdf.demo_kotlin.utils.FragmentUtil
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_media.*
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    private fun initView() {
        var learnFragment = LearnFragment()
        val mediaFragment = MediaFragment()
        val emptyFragment = EmptyFragment()

        rg_main_bottom_navigation.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rbtn_main_bottom_learn -> FragmentUtil.replaceFragment(supportFragmentManager, learnFragment)
                R.id.rbtn_main_bottom_media -> FragmentUtil.replaceFragment(supportFragmentManager, mediaFragment)
                R.id.rbtn_main_bottom_tools -> {
                    toast("工具")
                    FragmentUtil.replaceFragment(supportFragmentManager, emptyFragment)
                }
            }
        }
        rbtn_main_bottom_media.isChecked = true
    }
}
