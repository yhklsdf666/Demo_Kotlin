package com.yhklsdf.demo_kotlin.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.yhklsdf.demo_kotlin.R
import com.yhklsdf.demo_kotlin.fragment.LearnFragment
import com.yhklsdf.demo_kotlin.fragment.MediaFragment
import com.yhklsdf.demo_kotlin.fragment.TalkAiFragment
import com.yhklsdf.demo_kotlin.utils.FragmentUtil
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    private fun initView() {
        var learnFragment = LearnFragment()
        val mediaFragment = MediaFragment()
        val talkaiFragment = TalkAiFragment()
        //Activity重新创建时可以不完全销毁Fragment，当在onCreate()方法中调用了setRetainInstance(true)后，Fragment恢复时会跳过onCreate()和onDestroy()方法，因此不能在onCreate()中放置一些初始化逻辑
        learnFragment.retainInstance = true
        mediaFragment.retainInstance = true
        talkaiFragment.retainInstance = true

        rg_main_bottom_navigation.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rbtn_main_bottom_learn -> FragmentUtil.replaceFragment(supportFragmentManager, learnFragment)
                R.id.rbtn_main_bottom_media -> FragmentUtil.replaceFragment(supportFragmentManager, mediaFragment)
                R.id.rbtn_main_bottom_tools -> {
                    FragmentUtil.replaceFragment(supportFragmentManager, talkaiFragment)
//                    rg_main_bottom_navigation.visibility = View.GONE
                }
            }
        }
        rbtn_main_bottom_media.isChecked = true
    }
}
