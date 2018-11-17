package com.yhklsdf.demo_kotlin.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.yhklsdf.demo_kotlin.R

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        隐藏ActionBar
//        supportActionBar!!.hide()
        setContentView(R.layout.activity_splash)
    }
}
