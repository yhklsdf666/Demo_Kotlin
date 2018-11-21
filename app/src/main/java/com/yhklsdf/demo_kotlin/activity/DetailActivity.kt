package com.yhklsdf.demo_kotlin.activity

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebViewClient
import com.yhklsdf.demo_kotlin.R
import com.yhklsdf.demo_kotlin.event.LikeEvent
import com.yhklsdf.demo_kotlin.utils.RxBus
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    private var is_like = false
    private var type = ""

    private var url = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        initView()
        startWebView()
    }

    private fun initView() {
        /**
         * 设置标题和喜欢状态
         */
        val title = intent.getStringExtra("title")
        url = intent.getStringExtra("url")
        type = intent.getStringExtra("type")
        is_like = intent.getBooleanExtra("is_like", false)
        detail_title.text = title
        setLikeStatus()

        /**
         * 添加返回键和心键的监听事件
         */
        detail_back.setOnClickListener { finish() }
        detail_like.setOnClickListener {
            is_like = !is_like
            setLikeStatus()
            RxBus.getInstance().send(LikeEvent(type = type, url = url, is_like = is_like))
        }
    }

    private fun setLikeStatus() {
        if (is_like) {
            detail_like.setImageResource(R.mipmap.ic_love_full)
        } else {
            detail_like.setImageResource(R.mipmap.ic_love_empty)
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun startWebView() {
        val settings = detail_web_view.settings
        settings.javaScriptEnabled = true
        /*手指滑动缩放*/
        settings.setSupportZoom(true)
        /*当可视窗口超出了一个屏幕的范围时，缩小加载的HTML文件，已达到宽度和手机屏幕宽度一致的情况*/
        settings.useWideViewPort = true
        settings.loadWithOverviewMode = true
//        settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN

        detail_web_view.webViewClient = WebViewClient()

        val url0 = "https:$url"
        detail_web_view.loadUrl(url0)
    }
}
