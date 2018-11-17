package com.yhklsdf.demo_kotlin.adapter

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.yhklsdf.demo_kotlin.bean.PictureBean

class JTK {
    private val url = ""

    fun test(): PictureBean? {
        return Gson().fromJson<PictureBean>(url, object : TypeToken<List<PictureBean>>() {}.type)
    }
}
